package top.oldmoon.husoul.service;

import top.oldmoon.husoul.model.ClockHistory;
import top.oldmoon.husoul.model.ClockToday;
import top.oldmoon.husoul.model.ClockUser;

import java.util.List;

/**
 * @author hupg
 * @description 任务打卡业务层接口
 * @date 2022/2/21 10:54
 */
public interface ClockService {
    /**
     * 注册用户
     *
     * @param account 用户账号
     * @return oldmoon.api.http.ResultObject
     * @author hupg
     * @date 2022/2/21 11:05
     */
    ClockUser registerUser(String account);

    /**
     * 获取当天打卡信息
     *
     * @param clockToday 当天打卡信息
     * @return oldmoon.api.http.ResultObject
     * @author hupg
     * @date 2022/2/21 11:05
     */
    ClockToday getClockToday(ClockToday clockToday);

    /**
     * 获取历史打卡信息
     *
     * @param clockHistory 历史打卡信息
     * @return oldmoon.api.http.ResultObject
     * @author hupg
     * @date 2022/2/21 11:05
     */
    List<ClockHistory> getClockHistory(ClockHistory clockHistory);

    /**
     * 新增当天打卡信息
     *
     * @param clockToday 当天打卡信息
     * @return oldmoon.api.http.ResultObject
     * @author hupg
     * @date 2022/2/21 11:05
     */
    ClockToday addClockToday(ClockToday clockToday);

    /**
     * 更新当天打卡信息
     *
     * @param clockToday 当天打卡信息
     * @return oldmoon.api.http.ResultObject
     * @author hupg
     * @date 2022/2/21 11:05
     */
    ClockToday updateClockToday(ClockToday clockToday);
}
