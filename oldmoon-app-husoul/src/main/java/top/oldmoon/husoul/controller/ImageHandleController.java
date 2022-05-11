package top.oldmoon.husoul.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Description 图片操作
 * @Author liw2
 * @Date 2021/9/16 13:55
 */
@RestController
@RequestMapping("/imageHandle")
public class ImageHandleController {

//    @Value("${image.location}")
    private String imgLocation;

//    @Value("${image.uri}")
    private String imgUri;

    /**
     * @param file:
     * @Description: 图片上传
     * @Author liw2
     * @Date 13:56 2021/9/16
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upLoadImg(@RequestParam("file") MultipartFile file) throws IOException {
        // 获取到上传文件的名字
        String fileName = file.getOriginalFilename();
        fileName = System.currentTimeMillis() + "_" + fileName;

        File dir = new File(imgLocation, fileName);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // MultipartFile自带的解析方法
        file.transferTo(dir);
        return imgUri + fileName;
    }


    /**
     * 处理图片显示请求
     *
     * @param fileName
     */
    @GetMapping("/image/{fileName}.{suffix}")
    public void showPicture(@PathVariable("fileName") String fileName,
                            @PathVariable("suffix") String suffix,
                            HttpServletResponse response) {
        File imgFile = new File(imgLocation + fileName + "." + suffix);
        responseFile(response, imgFile);
    }


    /**
     * 响应输出图片文件
     *
     * @param response
     * @param imgFile
     */
    public static void responseFile(HttpServletResponse response, File imgFile) {
        try (InputStream is = new FileInputStream(imgFile);
             OutputStream os = response.getOutputStream();) {
            // 图片文件流缓存池
            byte[] buffer = new byte[1024];
            while (is.read(buffer) != -1) {
                os.write(buffer);
            }
            os.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
