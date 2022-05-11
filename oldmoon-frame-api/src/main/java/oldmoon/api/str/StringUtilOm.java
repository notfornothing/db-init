package oldmoon.api.str;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtilOm {

    public static boolean isEmpty(String str){
        if(str == null || "".equals(str.trim())) return true;
        return false;
    }

    public static boolean isNotEmpty(String str){
        if(str != null && !"".equals(str.trim())) return true;
        return false;
    }

    public static String initStringId(String front, String id) {
        if(isEmpty(id)) {
            return front + "" +System.currentTimeMillis();
        }
        return id;
    }


    private static final Map<String, Integer> numberMap = new HashMap();
    private static final List<String> numberList = new ArrayList();

    static {
        numberMap.put("零", 0);
        numberMap.put("一", 1);
        numberMap.put("二", 2);
        numberMap.put("三", 3);
        numberMap.put("四", 4);
        numberMap.put("五", 5);
        numberMap.put("六", 6);
        numberMap.put("七", 7);
        numberMap.put("八", 8);
        numberMap.put("九", 9);
        numberMap.put("十", 10);
        numberMap.put("百", 100);
        numberMap.put("千", 1000);
        numberMap.put("万", 10000);
        numberMap.put("亿", 100000000);
        numberList.add("亿");
        numberList.add("万");
        numberList.add("千");
        numberList.add("百");
        numberList.add("十");
        numberList.add("零");
    }

    /**
     * @description 中文大写数字转阿拉伯数字
     * @param numStr
     * @return
     */
    public static long chineseNumber2Number(String numStr) {
        long num = 0;
        for (String str : numberList) {
            if(numStr.contains(str)) {
                Integer numM = numberMap.get(str); // 单位
                String[] split = numStr.split(str);
                long numB = isEmpty(split[0]) ? 0 : chineseNumber2Number(split[0]); // 单位前数字
                long numE = split.length<2 ? 0 : chineseNumber2Number(split[1]); // 单位后数字
                num = numB * numM + numE;
                return num;
            }
        }
        return numberMap.get(numStr);
    }
    /**
     * 校验手机号
     * @author hupg
     * @date 2021/9/10 9:00
     * @param phoneNum
     * @return boolean true为正确手机号；false为错误手机号
     */
    public static boolean checkPhoneNum(String phoneNum){
        // 一般手机号校验（前三位数字）
        String isRegex = "^[1](([3][3-9])|([4][1,4-9])|([5][0-3,5-9])|([66])|([7][2,3,5-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$";
        // 两种前四位不存在的手机号校验
        String isNotRegex1 = "^(?!1349)\\d+$";
        String isNotRegex2 = "^(?!1578)\\d+$";
        Pattern isPattern = Pattern.compile(isRegex);
        Matcher isMatcher = isPattern.matcher(phoneNum);
        boolean isPhone1 = isMatcher.matches();

        Pattern isNotPattern1 = Pattern.compile(isNotRegex1);
        Matcher isNotMatcher1 = isNotPattern1.matcher(phoneNum);
        boolean isPhone2 = isNotMatcher1.matches();

        Pattern isNotPattern2 = Pattern.compile(isNotRegex2);
        Matcher isNotMatcher2 = isNotPattern2.matcher(phoneNum);
        boolean isPhone3 = isNotMatcher2.matches();
        return (isPhone1 && isPhone2 && isPhone3);
    }
}
