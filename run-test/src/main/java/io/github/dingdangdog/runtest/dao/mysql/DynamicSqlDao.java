package io.github.dingdangdog.runtest.dao.mysql;

import io.github.dingdangdog.runtest.entity.TableParam;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface DynamicSqlDao {
    List<String> getTableColumn(@Param("database") String database, @Param("tableName") String tableName);

    List<Map<String, Object>> getTable(@Param("tableInfo") TableParam tableParam,
                                       @Param("paramKeys") ArrayList<String> paramKeys,
                                       @Param("param") Map<String, Object> param);
}

