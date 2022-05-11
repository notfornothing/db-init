package top.oldmoon.husoul.service.impl;

import org.springframework.stereotype.Service;
import top.oldmoon.husoul.dao.UserDao;
import top.oldmoon.husoul.model.User;
import top.oldmoon.husoul.service.UserService;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserDao userDao;

    @Override
    public User getUser(User user) {
        return userDao.getUser(user);
    }
}
