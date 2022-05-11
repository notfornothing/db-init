package oldmoon.api.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @Description 线程工具
 * @Author hupg
 * @Date 2021/9/17 16:14
 */
public class ThreadTest {

    /**
     * 方式一测试：继承Thread类
     */
//    public static void main(String[] args) throws Exception {
//        Thread myThread = new MyThread();
//        myThread.start();
//    }

    /**
     * 方式二测试：实现Runnable接口
     */
//    public static void main(String[] args) throws Exception {
//        Runnable myRunnable = new MyRunnable();
//        Thread myRunnableThread = new Thread(myRunnable);
//        myRunnableThread.start();
//    }

    /**
     * 方式三测试：实现Callable接口
     */
    public static void main(String[] args) throws Exception {
        Callable myCallable = new MyCallable();
        FutureTask myFutureTask = new FutureTask<>(myCallable);
        Thread myThreadCallable = new Thread(myFutureTask);
        myThreadCallable.start();
        System.out.println(myFutureTask.get());

    }


    /**
     * 三种方式一起测试，观察多线程效果
     */
//    public static void main(String[] args) throws Exception {
//        // 创建Thread
//        Thread myThread = new MyThread();
//        // 创建Runnable
//        Runnable myRunnable = new MyRunnable();
//        Thread myRunnableThread = new Thread(myRunnable);
//        // 创建Callable
//        Callable myCallable = new MyCallable();
//        FutureTask myFutureTask = new FutureTask<>(myCallable);
//        Thread myThreadCallable = new Thread(myFutureTask);
//
//        // 启动Callable
//        myThreadCallable.start();
//        // 启动Thread
//        myThread.start();
//        // 启动Runnable
//        myRunnableThread.start();
//
//        System.out.println(myFutureTask.get());
//    }

}
