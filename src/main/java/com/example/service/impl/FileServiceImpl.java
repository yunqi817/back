package com.example.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.*;
import com.aliyuncs.exceptions.ClientException;
import com.example.config.OSSConfig;
import com.example.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 文件上传业务类
 *
 * @author lixiang
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Autowired
    private OSSConfig ossConfig;


//返回图片以及对应的文件名称
    @Override
    public Map<String, String> PicExcel() throws ClientException {
        String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
        // 填写Endpoint对应的Region信息，例如cn-hangzhou。
        String region = "cn-hangzhou";

        // 从环境变量中获取访问凭证。运行本代码示例之前，请先配置环境变量。
        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();

        // 创建OSSClient实例。
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        // 显式声明使用 V4 签名算法
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        OSS ossClient = OSSClientBuilder.create()
                .endpoint(endpoint)
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(region)
                .build();

        //返回的数据map
        Map<String, String> returnMap= new HashMap<>();
        // 列举图片文件并处理。
        ListObjectsV2Result objectListingPic = ossClient.listObjectsV2(ossConfig.getBucketName(),"picture/");
        List<OSSObjectSummary> pic = objectListingPic.getObjectSummaries();
        pic = pic.stream().filter(x -> !x.getKey().equals("picture/")).collect(Collectors.toList());
        List<String> keys =  pic.stream()
                .map(OSSObjectSummary::getKey)
                .collect(Collectors.toList());
        keys = keys.stream()
                .filter(key -> !key.equals("picture/"))
                .map(key -> key.substring(key.lastIndexOf('/') + 1)).collect(Collectors.toList());

        // 列举Excel文件并处理
        ListObjectsV2Result objectListingExcel = ossClient.listObjectsV2(ossConfig.getBucketName(),"excel/");
        List<OSSObjectSummary> excel = objectListingExcel.getObjectSummaries();
        excel = excel.stream().filter(x -> !x.getKey().equals("excel/")).collect(Collectors.toList());
        List<String> values =  excel.stream()
                .map(OSSObjectSummary::getKey)
                .collect(Collectors.toList());
        values = values.stream()
                .filter(key -> !key.equals("excel/"))
                .map(key -> key.substring(key.lastIndexOf('/') + 1)).collect(Collectors.toList());

        for (String key : keys) {
            String keyTemp = key.substring(0,key.indexOf("."));
            boolean found = false;
            for (String value : values) {
                String valueTemp = value.substring(0,value.indexOf("."));
                if (keyTemp.equals(valueTemp)) {
                    returnMap.put(key, value);
                    found = true;
                    break;
                }
            }
            if (!found) {
                returnMap.put(key, "无对应文件");
            }
        }


        return returnMap;
    }

    /**
     * 阿里云OSS文件上传
     *
     * @param file
     */
    @Override
    public String uploadPic(MultipartFile file) {

        //获取相关配置
        String bucketName = ossConfig.getBucketName();
        String endPoint = ossConfig.getEndPoint();
        String accessKeyId = ossConfig.getAccessKeyId();
        String accessKeySecret = ossConfig.getAccessKeySecret();

        //创建OSS对象
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

        //获取原生文件名
        String originalFilename = file.getOriginalFilename();
        //JDK8的日期格式
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        //拼装OSS上存储的路径
        String folder = dft.format(time);
        String fileName = generateUUID();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        //在OSS上bucket下的文件名
//        String uploadFileName = "picture/" +  fileName + extension;
        String uploadFileName = "picture/" + originalFilename;
        try {
            PutObjectResult result = ossClient.putObject(bucketName, uploadFileName, file.getInputStream());
            //拼装返回路径
            if (result != null) {

                recognize(originalFilename,"https://pic-excel.oss-cn-hangzhou.aliyuncs.com/picture/" + originalFilename,true);
                return "https://"+bucketName+"."+endPoint+"/"+uploadFileName;
            }
        } catch (IOException e) {
            throw  new RuntimeException(e);
        } finally {
            //OSS关闭服务，不然会造成OOM
            ossClient.shutdown();
        }
        return "true";
    }

    @Override
    public String uploadExcel(MultipartFile file) {

        //获取相关配置
        String bucketName = ossConfig.getBucketName();
        String endPoint = ossConfig.getEndPoint();
        String accessKeyId = ossConfig.getAccessKeyId();
        String accessKeySecret = ossConfig.getAccessKeySecret();

        //创建OSS对象
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

        //获取原生文件名
        String originalFilename = file.getOriginalFilename();
        //JDK8的日期格式
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        //拼装OSS上存储的路径
        String folder = dft.format(time);
        String fileName = generateUUID();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        //在OSS上bucket下的文件名
//        String uploadFileName = "picture/" + folder + "/" + fileName + extension;
        String uploadFileName = "excel/" + originalFilename;
        try {
            PutObjectResult result = ossClient.putObject(bucketName, uploadFileName, file.getInputStream());
            //拼装返回路径
            if (result != null) {
                return "https://"+bucketName+"."+endPoint+"/"+uploadFileName;
            }
        } catch (IOException e) {
            throw  new RuntimeException(e);
        } finally {
            //OSS关闭服务，不然会造成OOM
            ossClient.shutdown();
        }
        return null;
    }


    /**
     * 获取随机字符串
     * @return
     */
    private String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }



    public  String recognize(String fileName,String input, boolean isUrl) {
        // 通用表格识别
        String url = "https://api.textin.com/ai/service/v2/recognize/table/multipage";
        // 请登录后前往 “工作台-账号设置-开发者信息” 查看 x-ti-app-id
        // 示例代码中 x-ti-app-id 非真实数据
        String appId = "2fd5c702c68825e20e9d2caae277a853";
        // 请登录后前往 “工作台-账号设置-开发者信息” 查看 x-ti-secret-code
        // 示例代码中 x-ti-secret-code 非真实数据
        String secretCode = "013c5298f85f9298c768ab935ef1c9e3";

        BufferedReader in = null;
        DataOutputStream out = null;
        String result = "";

        try {
            byte[] requestData;

            // 根据 isUrl 决定请求体
            if (isUrl) {
                requestData = input.getBytes("UTF-8"); // 将 URL 转为字节数组
            } else {
                requestData = readFile(input); // 读取文件内容
            }

            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", isUrl ? "text/plain" : "application/octet-stream");
            conn.setRequestProperty("x-ti-app-id", appId);
            conn.setRequestProperty("x-ti-secret-code", secretCode);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");

            // 发送请求体
            out = new DataOutputStream(conn.getOutputStream());
            out.write(requestData);
            out.flush();

            // 读取响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            saveToExcel(fileName,result);
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 读取文件内容为字节数组
     *
     * @param path 文件路径
     * @return     文件的字节数组
     */
    public static byte[] readFile(String path) {
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(path);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

        public void saveToExcel(String fileName,String resultJson) {
            // 创建一个新的工作簿
            Workbook wb = new XSSFWorkbook();
            // 创建一个工作表
            Sheet ws = wb.createSheet();

            // 解析 JSON 字符串
            JSONObject result = new JSONObject(resultJson);
            JSONArray pages = result.optJSONObject("result").optJSONArray("pages");
            if (pages != null) {
                for (int i = 0; i < pages.length(); i++) {
                    JSONObject page = pages.getJSONObject(i);
                    JSONArray tables = page.optJSONArray("tables");
                    if (tables != null) {
                        for (int j = 0; j < tables.length(); j++) {
                            JSONObject table = tables.getJSONObject(j);
                            JSONArray cells = table.optJSONArray("table_cells");
                            if (cells != null) {
                                for (int k = 0; k < cells.length(); k++) {
                                    JSONObject cell = cells.getJSONObject(k);
                                    int rowIndex = cell.getInt("start_row");
                                    int colIndex = cell.getInt("start_col");
                                    String text = cell.getString("text");
                                    text = text.replace("→", ",").replace("、", "");

                                    // 获取或创建行
                                    Row row = ws.getRow(rowIndex);
                                    if (row == null) {
                                        row = ws.createRow(rowIndex);
                                    }
                                    // 获取或创建单元格
                                    Cell excelCell = row.getCell(colIndex);
                                    if (excelCell == null) {
                                        excelCell = row.createCell(colIndex);
                                    }
                                    // 设置单元格的值
                                    excelCell.setCellValue(text);
                                }
                            }
                        }
                    }
                }
            }

            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                wb.write(bos);
                byte[] excelBytes = bos.toByteArray();
                // 创建 MockMultipartFile 对象
                MultipartFile multipartFile = new MockMultipartFile(
                        "file",
                        fileName+".xlsx",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                        excelBytes
                );
                // 调用 upload 函数
                uploadExcel(multipartFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



}
