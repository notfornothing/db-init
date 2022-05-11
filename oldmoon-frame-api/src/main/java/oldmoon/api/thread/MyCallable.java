package oldmoon.api.thread;

import java.util.concurrent.Callable;

/**
 * @Description 实现Callable接口，重写Call方法
 * @Author hupg
 * @Date 2021/9/17 16:31
 */
public class MyCallable implements Callable {
    @Override
    public Object call() throws Exception {
        for (int i = 0; i < 10; i++) {
            System.out.printf("-----MyCallable执行%d次,%d-----\n", i + 1, System.currentTimeMillis());
            Thread.sleep(3000);
        }
        return "-----MyCallable执行完成-----";
    }
}
