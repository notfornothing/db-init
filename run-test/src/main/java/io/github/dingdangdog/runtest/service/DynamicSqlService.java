package io.github.dingdangdog.runtest.service;

import io.github.dingdangdog.runtest.entity.TableParam;

import java.util.List;
import java.util.Map;

/**
 * 动态SQL业务层
 *
 * @author DingDangDog
 * @since 2022/8/16 14:27
 */
public interface DynamicSqlService {
    List<Map<String, Object>> getTable(TableParam tableParam);
}
