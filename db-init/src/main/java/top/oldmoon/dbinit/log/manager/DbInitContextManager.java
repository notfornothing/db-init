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
    /**
     * 线程缓存管理器，用于管理线程绑定的{@link DbInitContext}
     */
    private static final ThreadLocal<DbInitContext> THREAD_LOCAL = new ThreadLocal<>();
    /**
     * 东8区时差
     */
    private static final int OFFSET = 8 * 60 * 60;

    /**
     * 开始，初始化上下文
     * <p>if you used it, don't forget use {@link DbInitContextManager}.end()
     *
     * @return {@link DbInitContext} 上下文对象
     * @author hupg
     * @date 2022/5/12 15:51
     */
    public static DbInitContext begin(String dbName) {
        DbInitContext context = getContext();
        if (context == null) {
            context = new DbInitContext();
            context.setId(Thread.currentThread().getId() + System.currentTimeMillis());
            context.setBeginTime(LocalDateTime.now());
            THREAD_LOCAL.set(context);
        }
        context.setDbName(dbName);
        return context;
    }

    /**
     * 获取当前线程中{@link DbInitContext}
     *
     * @return {@link DbInitContext} 上下文对象
     * @author hupg
     * @date 2022/5/12 17:42
     */
    public static DbInitContext getContext() {
        return THREAD_LOCAL.get();
    }

    /**
     * 移除当前线程中的 {@link DbInitContext}
     *
     * @author hupg
     * @date 2022/5/12 17:40
     */
    public static void end() {
        DbInitContext dbInitContext = THREAD_LOCAL.get();
        dbInitContext.setEndTime(LocalDateTime.now());
        dbInitContext.setTotalTimes(dbInitContext.getEndTime().toInstant(ZoneOffset.ofTotalSeconds(OFFSET)).toEpochMilli()
                - dbInitContext.getBeginTime().toInstant(ZoneOffset.ofTotalSeconds(OFFSET)).toEpochMilli());
        THREAD_LOCAL.remove();
    }
}
