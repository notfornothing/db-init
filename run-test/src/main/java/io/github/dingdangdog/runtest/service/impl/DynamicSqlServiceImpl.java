package io.github.dingdangdog.runtest.service.impl;

import io.github.dingdangdog.runtest.dao.mysql.DynamicSqlDao;
import io.github.dingdangdog.runtest.entity.TableParam;
import io.github.dingdangdog.runtest.service.DynamicSqlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * 动态SQL业务层
 *
 * @author DingDangDog
 * @since 2022/8/16 14:27
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class DynamicSqlServiceImpl implements DynamicSqlService {
    private final DynamicSqlDao dynamicSqlDao;

    public List<String> getTableColumn(String database, String tableName) {
        return dynamicSqlDao.getTableColumn(database, tableName);
    }

    @Override
    public List<Map<String, Object>> getTable(TableParam tableParam) {
        List<String> tableColumn = getTableColumn(tableParam.getDatabase(), tableParam.getTableName());
        Map<String, Object> param = tableParam.getParam();
        ArrayList<String> paramKeys = checkParam(tableColumn, param);
        if (CollectionUtils.isEmpty(paramKeys)) {
            return null;
        }
        return dynamicSqlDao.getTable(tableParam, paramKeys, param);
    }

    public ArrayList<String> checkParam(List<String> tableColumn, Map<String, Object> param) {
        ArrayList<String> paramKeys = new ArrayList<>(param.keySet());
        AtomicBoolean result = new AtomicBoolean(true);
        param.keySet().forEach(column -> {
            if (!tableColumn.contains(column)) {
                result.set(false);
                log.error("Column({}) does not exist in the table", column);
                paramKeys.remove(column);
            }
            if (param.get(column) == null || StringUtils.isEmpty(param.get(column).toString())) {
                result.set(false);
                paramKeys.remove(column);
            }
        });
        return paramKeys;
    }

}
