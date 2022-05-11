package top.oldmoon.husoul.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import oldmoon.api.http.ResultObject;
import oldmoon.api.http.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.oldmoon.husoul.model.ClockHistory;
import top.oldmoon.husoul.model.ClockToday;
import top.oldmoon.husoul.service.ClockService;

/**
 * @author hupg
 * @description 任务打卡控制层
 * @date 2022/2/21 10:54
 */
@RestController
@RequestMapping("/clock")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClockController {

    private final ClockService clockService;

    /**
     * 注册用户
     *
     * @param account 用户账号
     * @return oldmoon.api.http.ResultObject
     * @author hupg
     * @date 2022/2/21 11:05
     */
    @PostMapping("/registerUser")
    public ResultObject registerUser(@NonNull String account) {
        return ResultUtil.success(clockService.registerUser(account));
    }

    /**
     * 获取当天打卡信息
     *
     * @param clockToday 当天打卡信息
     * @return oldmoon.api.http.ResultObject
     * @author hupg
     * @date 2022/2/21 11:05
     */
    @GetMapping("/getClockToday")
    public ResultObject getClockToday(ClockToday clockToday) {
        return ResultUtil.success(clockService.getClockToday(clockToday));
    }

    /**
     * 获取历史打卡信息
     *
     * @param clockHistory 历史打卡信息
     * @return oldmoon.api.http.ResultObject
     * @author hupg
     * @date 2022/2/21 11:05
     */
    @GetMapping("/getClockHistory")
    public ResultObject getClockHistory(ClockHistory clockHistory) {
        return ResultUtil.success(clockService.getClockHistory(clockHistory));
    }

    /**
     * 添加当天打卡信息
     *
     * @param clockToday 当天打卡信息
     * @return oldmoon.api.http.ResultObject
     * @author hupg
     * @date 2022/2/21 11:05
     */
    @PostMapping("/addClockToday")
    public ResultObject addClockToday(@RequestBody ClockToday clockToday) {
        return ResultUtil.success(clockService.addClockToday(clockToday));
    }

    /**
     * 更新当天打卡信息
     *
     * @param clockToday 当天打卡信息
     * @return oldmoon.api.http.ResultObject
     * @author hupg
     * @date 2022/2/21 11:05
     */
    @PostMapping("/updateClockToday")
    public ResultObject updateClockToday(@RequestBody ClockToday clockToday) {
        return ResultUtil.success(clockService.updateClockToday(clockToday));
    }

}
