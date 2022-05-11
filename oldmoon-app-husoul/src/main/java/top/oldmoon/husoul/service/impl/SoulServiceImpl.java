package top.oldmoon.husoul.service.impl;

import oldmoon.api.file.FileUtilOm;
import oldmoon.api.http.ResultObject;
import oldmoon.api.http.ResultUtil;
import oldmoon.api.str.StringUtilOm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.oldmoon.husoul.dao.SoulDao;
import top.oldmoon.husoul.model.Soul;
import top.oldmoon.husoul.model.SoulType;
import top.oldmoon.husoul.service.SoulService;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * @Description soul业务实现层
 * @Author hupg
 * @Date 2021/7/2 13:54
 */
@Service
public class SoulServiceImpl implements SoulService {
    @Resource
    SoulDao soulDao;

    @Value("${file.default_path}")
    private String default_path;

    @Override
    public List<SoulType> getTypes(String type_no) {
        return soulDao.getTypes(type_no);
    }

    @Override
    public ResultObject saveType(SoulType soulType) {
        if(StringUtilOm.isEmpty(soulType.getId())){
            if(soulDao.getSoulTypeByNameAndType(soulType) > 0){
                throw new RuntimeException("已有同名胡说分类，请修改分类名称！");
            }
            soulType.setId(StringUtilOm.initStringId("soul", null));
            soulDao.saveSoulType(soulType);

        } else {
            soulDao.updateSoulTypeById(soulType);
        }

        return ResultUtil.success(soulType.getId());
    }

    @Override
    public List<Soul> getSouls(String soul_name, String soul_type, String status) {
        return soulDao.getSouls(soul_name, soul_type, status);
    }

    @Override
    public ResultObject saveSoul(Soul soul) {
        if(StringUtilOm.isEmpty(soul.getId())){
            if(soulDao.getSoulNumByNameAndType(soul) > 0){
                throw new RuntimeException("同类型下已经有同标题胡说，请修改标题！");
            }
            soul.setId(StringUtilOm.initStringId("soul", null));
            soulDao.saveSoul(soul);

        } else {
            soulDao.updateSoulById(soul);
        }
        FileUtilOm.saveFile(default_path+ "\\" + soul.getSoul_type(), soul.getSoul_name(), soul.getSoul_info());

        return ResultUtil.success(soul.getId());
    }

    @Override
    public Soul getSoulInfo(String id) {
        Soul soul = soulDao.getSoulInfo(id);

        if (soul != null){
            String fileInfo = FileUtilOm.getFileInfo(new File(default_path + "\\" + soul.getSoul_type() + "\\" + soul.getSoul_name()));
            soul.setSoul_info(fileInfo);
        }

        return soul;
    }
}
