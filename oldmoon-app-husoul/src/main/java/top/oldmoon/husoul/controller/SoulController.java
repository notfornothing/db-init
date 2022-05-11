package top.oldmoon.husoul.controller;

import oldmoon.api.http.ResultObject;
import oldmoon.api.http.ResultUtil;
import org.springframework.web.bind.annotation.*;
import top.oldmoon.husoul.model.Soul;
import top.oldmoon.husoul.model.SoulType;
import top.oldmoon.husoul.service.SoulService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description soul接口层
 * @Author hupg
 * @Date 2021/7/2 13:54
 */
@RestController
@ResponseBody
@RequestMapping("/soul")
public class SoulController {
    @Resource
    SoulService soulService;

    /**
     * @Description 查询分类及其soul数量
     * @Author hupg
     * @Date 2021/5/10 15:59
     */
    @GetMapping("/getTypes")
    public ResultObject getTypes(String type_no) {
        List<SoulType> mapList = soulService.getTypes(type_no);
        return ResultUtil.success(mapList);
    }

    /**
     * @Description 保存soul信息（数据库及文件）
     * @Author hupg
     * @Date 2021/5/12 11:19
     */
    @PostMapping("/saveType")
    public ResultObject saveType(@RequestBody SoulType soulType) {
        return soulService.saveType(soulType);
    }

    /**
     * @Description 查询soul基本信息
     * @Author hupg
     * @Date 2021/5/10 15:59
     */
    @GetMapping("/getSouls")
    public ResultObject getSouls(String soul_name, String soul_type, String status) {
        List<Soul> mapList = soulService.getSouls(soul_name, soul_type, status);
        return ResultUtil.success(mapList);
    }

    /**
     * @Description 保存soul信息（数据库及文件）
     * @Author hupg
     * @Date 2021/5/12 11:19
     */
    @PostMapping("/saveSoul")
    public ResultObject saveSoul(@RequestBody Soul soul) {
        return soulService.saveSoul(soul);
    }

    /**
     * @Description 根据ID获取soul信息（数据库及文件）
     * @Author hupg
     * @Date 2021/5/12 11:20
     */
    @GetMapping("/getSoulInfo")
    public ResultObject getSoulInfo(String id) {
        Soul soul = soulService.getSoulInfo(id);
        return ResultUtil.success(soul);
    }

}
