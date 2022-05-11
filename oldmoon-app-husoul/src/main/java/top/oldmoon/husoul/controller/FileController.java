package top.oldmoon.husoul.controller;

import oldmoon.api.http.ResultObject;
import oldmoon.api.http.ResultUtil;
import org.springframework.web.bind.annotation.*;
import top.oldmoon.husoul.model.FileModel;
import top.oldmoon.husoul.service.FileService;

import javax.annotation.Resource;
import java.util.List;

//@RestController
@ResponseBody
@RequestMapping("/file")
public class FileController {
    @Resource
    FileService fileService;

    /**
     * @Description 获取文件名集合
     * @Author hupg
     * @Date 2021/5/10 11:12
     */
    @GetMapping("/getFileNames")
    public ResultObject getFileNames(String filePath) {
        List<String> fileNames = fileService.getFileNames(filePath);
        return ResultUtil.success(fileNames);
    }

    /**
     * @Description 根据文件名获取文件内容
     * @Author hupg
     * @Date 2021/5/10 10:47
     */
    @GetMapping("/getFileInfo")
    public ResultObject getFileInfo(String filePath, String fileName) {
        String fileInfo = fileService.getFileInfo(filePath, fileName);
        return ResultUtil.success(fileInfo);
    }

    /**
     * @Description 保存文件
     * @Author hupg
     * @Date 2021/5/10 11:12
     */
    @PostMapping("/saveFile")
    public ResultObject saveFile(@RequestBody FileModel fileModel) {
        fileService.saveFile(fileModel);
        return ResultUtil.success();
    }


}

