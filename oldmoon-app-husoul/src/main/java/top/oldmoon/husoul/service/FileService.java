package top.oldmoon.husoul.service;

import top.oldmoon.husoul.model.FileModel;

import java.util.List;

public interface FileService {
    List<String> getFileNames(String filePath);

    String getFileInfo(String filePath, String fileName);

    void saveFile(FileModel fileModel);
}
