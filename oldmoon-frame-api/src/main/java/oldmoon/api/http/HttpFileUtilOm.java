package oldmoon.api.http;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @description http文件处理工具类
 */
public class HttpFileUtilOm {

    private static Logger logger = LoggerFactory.getLogger(HttpFileUtilOm.class);

    private static CloseableHttpClient httpClient = HttpClients.createDefault();

    /**
     * @param fileName 文件名
     * @param urlStr   接收服务地址
     * @return
     * @description httpPost发送一个文件
     */
    @SuppressWarnings("DuplicatedCode")
    public static boolean sendOneFile(String fileName, String urlStr) {
        return sendFiles(new String[]{fileName}, urlStr);
    }


    /**
     * @param fileNames 文件名数组
     * @param urlStr    接收服务地址
     * @return
     * @description httpPost发送多个文件
     */
    @SuppressWarnings("DuplicatedCode")
    public static boolean sendFiles(String fileNames[], String urlStr) {
        if (httpClient == null) {
            httpClient = HttpClients.createDefault();
        }
        HttpPost httpPost = new HttpPost(urlStr);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();

        for (int i = 0; i < fileNames.length; i++) {
            FileBody fileBody = new FileBody(new File(fileNames[i]));
            builder.addPart("file", fileBody);
        }
        httpPost.setEntity(builder.build());

        try {
            // 执行请求
            CloseableHttpResponse response = httpClient.execute(httpPost);

        } catch (Exception e) {
            logger.error("文件发送失败!", e.getMessage());
            return false;
        }
        logger.info("文件发送成功!");
        return true;
    }


    /**
     * @param filePath      文件目录
     * @param multipartFile 文件信息（http获取）
     * @return
     * @throws IOException
     * @description http接收的文件保存到本地
     */
    public static boolean saveFileForHttp(String filePath, MultipartFile multipartFile) throws IOException {
        // 判断目录是否存在，不存在则创建
        if (!new File(filePath).exists()) {
            new File(filePath).mkdirs();
        }

        String fileName = multipartFile.getOriginalFilename();
        FileInputStream input = (FileInputStream) multipartFile.getInputStream();
        File file = new File(filePath + System.currentTimeMillis() + "." + fileName);

        FileOutputStream output = new FileOutputStream(file);
        int b = 0;
        // 以字节流写入文件
        while ((b = input.read()) != -1) {
            output.write(b);
        }
        output.flush();
        input.close();
        output.close();
        return false;
    }

}
