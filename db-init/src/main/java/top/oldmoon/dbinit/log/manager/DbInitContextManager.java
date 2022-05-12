package top.oldmoon.dbinit.log.manager;

import top.oldmoon.dbinit.log.entity.DbInitContext;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 上下文管理者
 *
 * @author hupg
 * @date 2022/5/12 15:45
 */
public class DbInitContextManager {
    private static final ThreadLocal<DbInitContext> THREAD_LOCAL = new ThreadLocal<>();

    private static final int OFFSET = 8 * 60 * 60;

    /**
     * 开始，初始化上下文
     * <p>if you used it, don't forget use {@link DbInitContextManager}.end()
     *
     * @return context 上下文对象
     * @author hupg
     * @date 2022/5/12 15:51
     */
    public static DbInitContext begin() {
        DbInitContext context = getContext();
        if (context == null) {
            context = new DbInitContext();
            context.setId(Thread.currentThread().getId() + System.currentTimeMillis());
            context.setBeginTime(LocalDateTime.now());
            THREAD_LOCAL.set(context);
        }
        return context;
    }

    public static DbInitContext getContext() {
        return THREAD_LOCAL.get();
    }

    public static void end() {
        DbInitContext dbInitContext = THREAD_LOCAL.get();
        dbInitContext.setEndTime(LocalDateTime.now());
        dbInitContext.setTotalTimes(dbInitContext.getEndTime().toInstant(ZoneOffset.ofTotalSeconds(OFFSET)).toEpochMilli()
                - dbInitContext.getBeginTime().toInstant(ZoneOffset.ofTotalSeconds(OFFSET)).toEpochMilli());
        THREAD_LOCAL.remove();
    }
}
