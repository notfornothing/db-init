package top.oldmoon.husoul.dao;

import org.apache.ibatis.annotations.Mapper;
import top.oldmoon.husoul.model.ClockHistory;
import top.oldmoon.husoul.model.ClockToday;
import top.oldmoon.husoul.model.ClockUser;

import java.util.List;

/**
 * @author hupg
 * @description 任务打卡数据层
 * @date 2022/2/21 10:54
 */
@Mapper
public interface ClockDao {
    int insertUser(ClockUser clockUser);

    ClockUser getClockUser(ClockUser clockUser);

    ClockToday getClockToday(ClockToday clockToday);

    List<ClockHistory> getClockHistory(ClockHistory clockHistory);

    int addClockToday(ClockToday clockToday);

    int updateClockToday(ClockToday clockToday);
}
