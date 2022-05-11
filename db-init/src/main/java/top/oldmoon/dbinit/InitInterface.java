package top.oldmoon.dbinit;

/**
 * 数据库初始化基本接口管理
 *
 * @author hupg
 * @date 2022/5/10 16:24
 */
public interface InitInterface {
    /**
     * 初始化逻辑入口
     *
     * @author hupg
     * @date 2022/5/10 16:26
     */
    void init();

    /**
     * 结束前调用，关闭流
     *
     * @author hupg
     * @date 2022/5/10 16:46
     */
    void close();
}
