package top.oldmoon.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 文件操作工具类
 *
 * @author hupg
 * @date 2021
 */
public class FileUtilOm {

    /**
     * 获取根目录 resources 下指定文件夹所有文件
     *
     * @param dir 文件夹名称
     * @author hupg
     * @date 2022/5/11 10:09
     */
    public static File[] getRootFiles(String dir) {
        String path = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(dir)).getPath();
        return loadFilesByPath(path);
    }


    /**
     * @param path 文件路径
     * @return 文件名列表
     * @description 根据文件路径加载所有文件名
     */
    public static List<String> getFileNameByPath(String path) {
        List<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
//                files.add(tempList[i].toString());
                //文件名，不包含路径
                String fileName = tempList[i].getName();
                files.add(fileName);
            }
            if (tempList[i].isDirectory()) {
                //这里就不递归了，
            }
        }
        return files;
    }

    /**
     * @param path 文件路径
     * @return 文件列表
     * @description 根据文件路径加载所有一级子文件（包括文件夹）
     */
    public static File[] loadFilesByPath(String path) {
        File file = new File(path);
        return file.listFiles();
    }

    /**
     * @param path 文件路径
     * @return 文件对象
     * @description 根据文件路径加载单个文件
     */
    public static File loadFileInfoByFile(String path) {
        return new File(path);
    }

    public static String getFileInfo(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String str;
            while ((str = in.readLine()) != null) {
                stringBuilder.append(str);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return stringBuilder.toString();
    }


    /**
     * @param filePath 文件路径
     * @param fileName 文件名
     * @param fileInfo 文件内容
     * @description 根据入参，保存文件
     */
    public static void saveFile(String filePath, String fileName, String fileInfo) {
        File file = new File(filePath + fileName);
        try (FileWriter fw = new FileWriter(file)) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            //创建字符流（使用字节流比较麻烦）

            //判断file是否存在
            if (!file.exists()) {
                //如果不存在file文件，则创建
                file.createNewFile();
            }
            fw.write(fileInfo);
            //这里要说明一下，write方法是写入缓存区，并没有写进file文件里面，要使用flush方法才写进去
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
