package io.github.dingdangdog.runtest.controller;

import io.github.dingdangdog.runtest.entity.TableParam;
import io.github.dingdangdog.runtest.service.DynamicSqlService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 动态SQL入口
 *
 * @author DingDangDog
 * @since 2022/8/16 14:34
 */
@RestController
@RequestMapping("/dynamicSql")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DynamicSqlController {
    private final DynamicSqlService dynamicSqlService;

    @PostMapping("/getTable")
    public List<Map<String, Object>> getTable(@RequestBody TableParam tableParam) {
        return dynamicSqlService.getTable(tableParam);
    }
}
