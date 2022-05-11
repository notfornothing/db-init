package oldmoon.api.encrypt;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

public class EncryptUtilOm {


    /**
     * @param str
     * @return
     * @description 获取MD5签名
     */
    public static String getSign(String str) {
        return encryptionByMD5(str);
    }

    /**
     * @return
     * @deacription MD5加密_MD5加密是不可逆的
     */
    private static String encryptionByMD5(String enStr) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] inputData = enStr.getBytes();
            String s = encoderBASE64(md.digest(inputData));
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return
     * @throws Exception
     * @description BASE64编码
     */
    private static String encoderBASE64(byte[] key) {
        return Base64.encodeBase64String(key);
    }

    /**
     * @return
     * @throws Exception
     * @description BASE64解码
     */
    private static byte[] decoderBASE64(String str) {
        return Base64.decodeBase64(str);
    }


    /**
     * @param passwd 初始密码
     * @param salt   盐
     * @return
     * @description 加盐加密
     */
    public static String encryptionBySalt(String passwd, String salt) {
        return encryptionByMD5(passwd + salt);
    }

}
