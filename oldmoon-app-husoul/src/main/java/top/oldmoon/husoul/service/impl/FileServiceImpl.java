package top.oldmoon.husoul.service.impl;

import lombok.extern.slf4j.Slf4j;
import oldmoon.api.file.FileUtilOm;
import org.springframework.stereotype.Service;
import top.oldmoon.husoul.model.FileModel;
import top.oldmoon.husoul.service.FileService;

import java.io.File;
import java.util.List;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Override
    public List<String> getFileNames(String filePath) {
        List<String> fileNames = FileUtilOm.getFileNameByPath(filePath);
        return fileNames;
    }

    @Override
    public String getFileInfo(String filePath, String fileName) {
        String fileInfo = FileUtilOm.getFileInfo(new File(filePath + "/" + fileName));
//        log.info("获取文件内容成功：{}", fileInfo);
        return fileInfo;
    }

    @Override
    public void saveFile(FileModel fileModel) {
        FileUtilOm.saveFile(fileModel.getFilePath(), fileModel.getFileName(), fileModel.getFileInfo());
    }
}
