package top.oldmoon.husoul.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.oldmoon.husoul.model.Soul;
import top.oldmoon.husoul.model.SoulType;

import java.util.List;

/**
 * @Description soul数据层
 * @Author hupg
 * @Date 2021/7/2 13:53
 */
@Mapper
public interface SoulDao {
    List<SoulType> getTypes(@Param("type_no") String type_no);

    List<Soul> getSouls(@Param("soul_name") String soul_name, @Param("soul_type") String soul_type, @Param("status") String status);

    Soul getSoulInfo(@Param("id") String id);

    int getSoulNumByNameAndType(@Param("soul") Soul soul);

    void saveSoul(@Param("soul") Soul soul);

    void updateSoulById(@Param("soul") Soul soul);

    int getSoulTypeByNameAndType(@Param("soulType") SoulType soulType);

    void saveSoulType(@Param("soulType") SoulType soulType);

    void updateSoulTypeById(@Param("soulType") SoulType soulType);
}
