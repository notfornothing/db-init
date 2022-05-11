package oldmoon.api.thread;

/**
 * @Description 集成Thread类，重写run方法
 * @Author hupg
 * @Date 2021/9/17 16:30
 */
public class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.printf("-----MyThread执行%d次,%d-----\n", i + 1, System.currentTimeMillis());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
