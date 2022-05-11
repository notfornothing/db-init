package oldmoon.api.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * @program: sgitg-micro-service
 * @description: HttpClient工具类
 **/
@Slf4j
public class HttpClientUtil {

    /**
     * @return java.lang.String
     * @Description get map参数
     * @Date 2020/12/8 13:56
     * @Param [url, param]
     **/
    public static String doGet(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * @param urlString 文件地址URL
     * @param filename  文件名（保存用）
     * @param savePath  文件夹（保存用）
     * @Description 根据url获取文件并保存
     * @Author hupg
     * @Date 2021/8/16 17:29
     */
    public static void doGetFile(String urlString, String filename, String savePath) {
        InputStream is = null;
        OutputStream os = null;
        try {
            // 构造URL
            URL url = new URL(urlString);
            // 打开连接
            URLConnection con = url.openConnection();
            // 设置请求超时为5s
            con.setConnectTimeout(5 * 1000);
            // 设置浏览器访问
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Safari/537.36");
            // 输入流
            is = con.getInputStream();

            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 创建文件夹
            File sf = new File(savePath);
            if (!sf.exists()) {
                sf.mkdirs();
            }
            // 创建文件输出流
            os = new FileOutputStream(savePath + filename);
            // 开始写文件
            // 读取到的数据长度
            int len;
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            log.info("------文件保存成功：{}", savePath + filename);
        } catch (Exception e) {
            log.error("------文件保存失败：{}", e.getMessage());
            //e.printStackTrace();
        } finally {
            try {
                // 完毕，关闭所有链接
                if (os != null) {
                    os.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @return java.lang.String
     * @Description get无参
     * @Date 2020/12/8 13:56
     * @Param [url]
     **/
    public static String doGet(String url) {
        return doGet(url, null);
    }

    /**
     * @return java.lang.String
     * @Description post map参数
     * @Date 2020/12/8 13:56
     * @Param [url, param]
     **/
    public static String doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * @return java.lang.String
     * @Description post head map参数
     * @Date 2021/04/28 15:15
     * @Param [url, headMap, param]
     **/
    public static String doPost(String url, Map<String, String> headMap, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                httpPost.setEntity(entity);
            }
            // 添加head参数
            if (headMap != null && !headMap.isEmpty()) {
                for (String key : headMap.keySet()) {
                    httpPost.addHeader(key, headMap.get(key));
                }
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

//    /**
//     * @Description 供服token方式POST请求通用方法
//     * @Author hupg
//     * @Date 2021/4/30 11:39
//     */
//    public static String doPostByGfToken(String url, Map<String, String> param, boolean again) {
//
//        Map<String, String> headMap = Common.getGfServiceToken();
//
//        // 创建Httpclient对象
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        CloseableHttpResponse response = null;
//        String resultString = "";
//        try {
//            // 创建Http Post请求
//            HttpPost httpPost = new HttpPost(url);
//            // 添加body-json格式参数
//            if (param != null) {
//                StringEntity entity = new StringEntity(JsonUtils.mapToString(param), ContentType.APPLICATION_JSON);
//                httpPost.setEntity(entity);
//            }
//            // 添加head参数
//            if (headMap != null && !headMap.isEmpty()) {
//                for (String key : headMap.keySet()) {
//                    httpPost.addHeader(key, headMap.get(key));
//                }
//            }
//            // 执行http请求
//            response = httpClient.execute(httpPost);
//            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
//
//            if (StringUtils.isNotEmpty(resultString)) {
//                Map<String, Object> hashMap = JSON.parseObject(resultString, Map.class);
//                // 共服token密钥失效处理
//                if("无权访问".equals(hashMap.get("error_description"))){
//                    // 清除redis共服密钥
//                    Common.delGfServiceToken();
//                    // 重新拉取token
//                    Common.getGfServiceToken();
//                    if(again) { // 递归再次调用业务接口
//                        return doPostByGfToken(url, param, false);
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (response != null) {
//                    response.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//                httpClient.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return resultString;
//    }


    public static String doPostByFormData(String url, Map<String, String> param) throws Exception {
        String result = "";
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        RequestConfig defaultRequestConfig = RequestConfig.custom().setSocketTimeout(6000).setConnectTimeout(5000)
                .setConnectionRequestTimeout(6000).setStaleConnectionCheckEnabled(true).build();
        client = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
        // client = HttpClients.createDefault();
        URIBuilder uriBuilder = new URIBuilder(url);

        HttpPost httpPost = new HttpPost(uriBuilder.build());
        httpPost.setHeader("Connection", "Keep-Alive");
        httpPost.setHeader("Charset", "UTF-8");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        Iterator<Map.Entry<String, String>> it = param.entrySet().iterator();
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
            params.add(pair);
        }

        httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        try {
            response = client.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, "UTF-8");
                }
            }
        } catch (ClientProtocolException e) {
            throw new RuntimeException("创建连接失败" + e);
        } catch (IOException e) {
            throw new RuntimeException("创建连接失败" + e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static String doPost(String url) {
        return doPost(url, null);
    }

    /**
     * @return java.lang.String
     * @Description post json参数
     * @Date 2020/12/8 13:56
     * @Param [url, param]
     **/
    public static String doPostJson(String url, String json) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

}
