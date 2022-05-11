package top.oldmoon.husoul.interceptor;

import oldmoon.api.str.StringUtilOm;
import oldmoon.api.token.TokenUtilOm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.oldmoon.husoul.common.ServerCache;
import top.oldmoon.husoul.config.Audience;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @Description token拦截器
 * @Author hupg
 * @Date 2021/5/14 14:03
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    Audience audience;
    @Autowired
    ServerCache serverCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 是否需要拦截
        if (!isNeedInterceptor(request)) {
            return true;
        }

        String token = request.getHeader("Token");
        // token非空
        if (StringUtilOm.isEmpty(token)) {
            return false;
        }
        // token信息正确（能获取userid）
        String userid = TokenUtilOm.getTokenInfoByKey("userid", audience.getPublicKey());
        if (StringUtilOm.isEmpty(userid)) {
            return false;
        }
        // token有效期
        if (!TokenUtilOm.verificationToken(token, audience.getPublicKey())) {
            serverCache.removeToken(userid);
            return false;
        }
        // token匹配
        String cacheToken = serverCache.getToken(userid);
        if (token.equals(cacheToken)) {
            return true;
        }

        return false;
    }

    // 不需要拦截的请求
    static String[] passUrls = new String[]{
            "/husoul/user/login",
            "/husoul/soul/getTypes",
            "/husoul/soul/getSouls",
            "/husoul/soul/getSoulInfo"
    };

    private boolean isNeedInterceptor(HttpServletRequest request) {

        return Arrays.asList(passUrls).contains(request.getRequestURI()) ? false : true;
    }

}
