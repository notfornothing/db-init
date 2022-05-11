package oldmoon.api.sql.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description 表信息
 * @Author hupg
 * @Date 2021/9/16 18:19
 */
@Data
@NoArgsConstructor
public class Table {
    private String name;
    private String key;
    private String desc;
    private List<Fie> fieList;
}
