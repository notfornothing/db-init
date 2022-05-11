package top.oldmoon.husoul.controller;

import oldmoon.api.encrypt.EncryptUtilOm;
import oldmoon.api.http.ResultObject;
import oldmoon.api.http.ResultUtil;
import oldmoon.api.str.StringUtilOm;
import oldmoon.api.token.TokenUtilOm;
import org.springframework.web.bind.annotation.*;
import top.oldmoon.husoul.common.ServerCache;
import top.oldmoon.husoul.config.Audience;
import top.oldmoon.husoul.model.User;
import top.oldmoon.husoul.service.UserService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;
    @Resource
    ServerCache serverCache;
    @Resource
    Audience audience;

    @PostMapping("/login")
    public ResultObject login(User user){
//        User user = new User();
//        user.setUsername(username);
//        user.setPassword(password);
        try{
            if(StringUtilOm.isNotEmpty(user.getUsername()) && StringUtilOm.isNotEmpty(user.getPassword())){
                // 加密
                user.setPassword(EncryptUtilOm.encryptionBySalt(user.getPassword(), user.getUsername()));
                // 验证数据库是否有该用户

                user = userService.getUser(user);

                if(user == null || StringUtilOm.isEmpty(user.getId())){
                    throw new RuntimeException("用户名或密码错误！");
                }
                // 生成token
                String token = TokenUtilOm.createToken(user.getId(), user.getUsername(), user.getPassword(), user.getName(),
                        audience.getServerId(), audience.getPublicKey(), audience.getDueTime());

                // 保存token
                serverCache.setToken(user.getId(), token);
                // 返回数据
                return ResultUtil.success(user, token);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.error(e.getMessage(),"");
        }

        return ResultUtil.error("用户名或密码为空","");
    }
}
