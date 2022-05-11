package top.oldmoon.husoul.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.oldmoon.husoul.dao.ClockDao;
import top.oldmoon.husoul.model.ClockHistory;
import top.oldmoon.husoul.model.ClockToday;
import top.oldmoon.husoul.model.ClockUser;
import top.oldmoon.husoul.service.ClockService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author hupg
 * @description 任务打卡业务层
 * @date 2022/2/21 10:54
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClockServiceImpl implements ClockService {

    private final ClockDao clockDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ClockUser registerUser(String account) {
        ClockUser clockUser = new ClockUser();
        clockUser.setUserAccount(account);
        clockUser.setCreateDate(LocalDateTime.now());
        int userId = clockDao.insertUser(clockUser);

        ClockToday clockToday = new ClockToday();
        clockToday.setUserId(userId);
        clockToday.setUserAccount(account);
        clockToday.setClockDate(LocalDateTime.now());
        int flag = clockDao.addClockToday(clockToday);

        clockUser = clockDao.getClockUser(clockUser);
        return clockUser;
    }

    @Override
    public ClockToday getClockToday(ClockToday clockToday) {
        return clockDao.getClockToday(clockToday);
    }

    @Override
    public List<ClockHistory> getClockHistory(ClockHistory clockHistory) {
        return clockDao.getClockHistory(clockHistory);
    }

    @Override
    public ClockToday addClockToday(ClockToday clockToday) {
        clockToday.setClockDate(LocalDateTime.now());
        int flag = clockDao.addClockToday(clockToday);
        return clockDao.getClockToday(clockToday);
    }

    @Override
    public ClockToday updateClockToday(ClockToday clockToday) {
        clockToday.setClockDate(LocalDateTime.now());
        int flag = clockDao.updateClockToday(clockToday);
        return clockDao.getClockToday(clockToday);
    }
}
