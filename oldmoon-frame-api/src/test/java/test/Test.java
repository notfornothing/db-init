package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
//    public static void main(String[] args) {
//        String address = "山东省济南市历城区凤凰北路赵家新苑小区";
//        String regex = "(?<province>.*?自治区|.*?省|.*?行政区|.*?市)(?<city>.*?自治州|.*?地区|.*?行政单位|市辖区|.*?市)?(?<county>.*?市|.*?县|.*?区)?(?<village>.*)";
//
//        Pattern compile = Pattern.compile(regex);
//        Matcher matcher = compile.matcher(address);
////        String province = matcher.group("province");
//        System.out.println(matcher.groupCount());
//
//        String province,city,county,village;
//        while(matcher.find()){
//            province = matcher.group("province");
//            city = matcher.group("city");
//            county = matcher.group("county");
//            village = matcher.group("village");
//            System.out.println(province);
//            System.out.println(city);
//            System.out.println(county);
//            System.out.println(village);
//        }
//    }

    public static void main(String[] args) {

        String [] strs = {"你号","你好","你真好","你非常好","你特别好"};
        List<String> strList = Arrays.asList(strs);



    }
}
