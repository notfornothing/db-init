package io.github.dingdangdog.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * 自定义数据库初始化逻辑的接口
 *
 * @author DingDangDog
 * @since 2022/5/10 16:36
 */
public interface DbInitRunner extends ApplicationRunner {

    /**
     * 执行初始化的入口
     *
     * @throws Exception 任何异常
     * @author DingDangDog
     * @since 2022/10/26 17:20
     */
    void init() throws Exception;

    /**
     * 校验配置是否正确
     *
     * @return true：配置正确；false：配置错误
     * @author DingDangDog
     * @since 2022/10/26 17:20
     */
    boolean checkConfig();

    /**
     * runner的run方法默认实现
     *
     * @param args 服务环境
     * @throws Exception 任何异常
     * @author DingDangDog
     * @since 2022/10/26 17:25
     */
    @Override
    default void run(ApplicationArguments args) throws Exception {
        if (this.checkConfig()) {
            this.init();
        }
    }
}
