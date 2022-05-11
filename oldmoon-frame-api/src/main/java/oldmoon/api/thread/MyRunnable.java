package oldmoon.api.thread;

/**
 * @Description 实现Runnable接口，实现run方法
 * @Author hupg
 * @Date 2021/9/17 16:31
 */
public class MyRunnable implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.printf("-----MyRunnable执行%d次,%d-----\n", i + 1, System.currentTimeMillis());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
