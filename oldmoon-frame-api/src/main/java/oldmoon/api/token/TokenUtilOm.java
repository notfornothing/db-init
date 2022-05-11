package oldmoon.api.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import oldmoon.api.str.StringUtilOm;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * @Description token处理工具类
 * @Author hupg
 * @Date 2021/5/14 12:03
 */
public class TokenUtilOm {

    /**
     * @Description 生成token
     * @Author hupg
     * @Date 2021/5/14 12:03
     */
    public static String createToken(String userid, String username, String password, String name, String serverId, String publicKey, int dueTime){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(publicKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // 添加构成JWT的参数
        JwtBuilder builder = Jwts.builder().setHeaderParam("type", "JWT")
                .claim("id", userid)
                .claim("username", username)
                .claim("password",password)
                .claim("name",name)
                .setIssuer(serverId)
                .setAudience(serverId)
                .signWith(signatureAlgorithm, signingKey);

        // 添加Token过期时间
        if (dueTime >= 0) {
            long expMillis = nowMillis + dueTime;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }

        // 生成JWT
        return builder.compact();
    }

    /**
     * 解析jwt
     */
    public static Claims parseJWT(String token, String publicKey){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(publicKey))
                    .parseClaimsJws(token).getBody();
            return claims;
        } catch(Exception ex) {
            return null;
        }
    }

    /**
     * 根据key从token里获取信息
     */
    public static String getTokenInfoByKey(String key, String publicKey) {
        // 获取http请求
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (StringUtilOm.isEmpty(publicKey)) {
            return "-1";
        }
        String token = request.getHeader("Token");
        Claims t = parseJWT(token, publicKey);
        return t.get(key).toString();
    }

    /**
     * @Description 校验token有效性
     * @Author hupg
     * @Date 2021/5/14 13:52
     */
    public static boolean verificationToken(String token, String publicKey){
        Claims claims = parseJWT(token, publicKey);

        // 判断token有效期
        Date expiration = claims.getExpiration();
        if(new Date().getTime() > expiration.getTime()){
            return false;
        }
        return true;
    }

}
