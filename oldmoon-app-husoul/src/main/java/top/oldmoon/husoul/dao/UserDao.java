package top.oldmoon.husoul.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.oldmoon.husoul.model.User;

@Mapper
public interface UserDao {
    User getUser(@Param("user") User user);
}
