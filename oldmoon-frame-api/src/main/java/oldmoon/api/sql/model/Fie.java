package oldmoon.api.sql.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 表字段
 * @Author hupg
 * @Date 2021/9/16 18:18
 */
@Data
@NoArgsConstructor
public class Fie {
    private String name;
    private String type;
    private String desc;
}
