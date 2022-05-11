package oldmoon.api.file;

import com.alibaba.fastjson.JSONArray;
import oldmoon.api.http.HttpClientUtil;
import oldmoon.api.http.HttpFileUtilOm;
import oldmoon.api.json.JsonUtilOm;
import oldmoon.api.str.StringUtilOm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {

    /**
     * @description 将数据转换成指定JSON格式，便于导入到数据库中
     */
//    public static void main(String[] args) {
//
//        List<String> fileNames = FileUtilOm.loadFileNameByPath("D:\\Downloads\\areaMap");
//        File[] fileInfos = FileUtilOm.loadFilesByPath("D:\\Downloads\\areaMap");
//        List<String> newFileNames = new ArrayList<>();
//        for (String fileName : fileNames) {
//            fileName = fileName.replace("sd", "");
//            fileName = fileName.replace(".geofile", "");
//            newFileNames.add(fileName);
//        }
//        System.out.println(newFileNames);
//
//
//        List<Map> fileInfo = new ArrayList<>();
//        for (File file : fileInfos) {
//            for (String fileName : newFileNames) {
//                if (file.getName().replace("sd", "").replace(".geofile", "").equals(fileName)) {
//                    try {
//                        BufferedReader in = new BufferedReader(new FileReader(file));
//                        StringBuilder json = new StringBuilder();
//                        String str;
//                        while ((str = in.readLine()) != null) {
//                            json.append(str);
//                        }
//                        Map<String, Object> oneMap = new HashMap<>();
//                        try{
////                            System.out.println(json);
//                            oneMap = JsonUtilOm.jsonStringToMapObject(json.toString().replaceAll("\r|\n", "").trim());
//                        } catch (Exception e) {
//                            e.getStackTrace();
//                        }
//                        List<Map> listMap = (List<Map>) oneMap.get("MapContent");
//                        for (Map map: listMap) {
//                            Map<String, String> columnInfo = new HashMap<>();
//                            columnInfo.put("SSQY", fileName);
//                            Map<String, String> attributesMap = (Map<String, String>) map.get("attributes");
//                            Map<String, String> geometryMap = (Map<String, String>) map.get("geometry");
////                                System.out.println(attributesMap.get("NAME"));
////                                System.out.println(attributesMap.get("ID"));
////                                System.out.println(JSONArray.toJSONString(geometryMap.get("rings")));
//                            columnInfo.put("SSQYMC", attributesMap.get("NAME"));
//                            columnInfo.put("PK_ID", attributesMap.get("ID"));
//                            columnInfo.put("RINGS", JSONArray.toJSONString(geometryMap.get("rings")));
////                                System.out.println("===================END");
//                            fileInfo.add(columnInfo);
//                        }
//                    } catch (IOException e) {
//                        e.getStackTrace();
//                    }
//                }
//            }
//        }
//        FileUtilOm.saveFile("D:\\Downloads\\json","areaMap.json", JsonUtilOm.listMapToJson(fileInfo));
//    }

    /**
     * @param args
     * @description 判断坐标数据是否有问题，将有问题的数据打印
     * 1、用英文","分割后数组长度是2
     * 2、分割后每一个部分都是一个正确的数字
     */
//    public static void main(String[] args) {
//        File file = FileUtilOm.loadFileInfoByFile("D:\\Downloads\\json\\array.txt");
//        List<String> warning = new ArrayList<>();
//        try{
//            BufferedReader in = new BufferedReader(new FileReader(file));
//            String str;
//            while ((str = in.readLine()) != null) {
//                if(StringUtilOm.isNotEmpty(str)){
//                    String[] strs = str.split(",");
//                    if (strs.length != 2){
//                        warning.add(str);
//                        continue;
//                    }
//                    for (String s: strs) {
//                        try {
//                            float v = Float.parseFloat(s);
//                        }catch (Exception e){
//                            warning.add(str);
//                            break;
//                        }
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.getStackTrace();
//        }
//        System.out.println(warning);
//    }

    /*public static void main(String[] args) {
        String str = "00";

        Map<String, String> map = new HashMap<>();
        map.put("Str", "00");
        change(map);
        System.out.println(map.get("Str"));
    }*/
    public static void main(String[] args) {
        String url = new String("http://www.ruanyifeng.com/images_pub/pub_");
        String tab = new String(".jpg");
        for (int i = 1; i <= 400; i++) {
            HttpClientUtil.doGetFile(url + i + tab, i + tab, "D:/pa/");
        }
    }

    private static void change(Map<String, String> map) {
        map.put("Str", "01");
    }

}
