package top.oldmoon.husoul.service;

import oldmoon.api.http.ResultObject;
import top.oldmoon.husoul.model.Soul;
import top.oldmoon.husoul.model.SoulType;

import java.util.List;

/**
 * @Description soul业务接口层
 * @Author hupg
 * @Date 2021/7/2 13:54
 */
public interface SoulService {
    List<SoulType> getTypes(String type_no);

    List<Soul> getSouls(String soul_name, String soul_type, String status);

    ResultObject saveSoul(Soul soul);

    Soul getSoulInfo(String id);

    ResultObject saveType(SoulType soulType);
}
