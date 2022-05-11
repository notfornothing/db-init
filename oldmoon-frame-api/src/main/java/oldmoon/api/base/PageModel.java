package oldmoon.api.base;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description 分页实体
 * @Author hupg
 * @Date 2021/9/17 15:38
 */
@Data
@NoArgsConstructor
public class PageModel<T> {

    private Integer totalNum;
    private Integer pageSize;
    private Integer currentPage;
    private Integer totalPage;
    private List<T> list;

}
