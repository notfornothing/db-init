package oldmoon.api.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

@Aspect
@Component
public class TimeOutAspect {

    Logger logger = LoggerFactory.getLogger(TimeOutAspect.class);

    @Pointcut("execution(public * top.oldmoon.bill.service..*(..))")
    public void function(){
    }


    @Around("function() && @annotation(toa)")
    public Object around(ProceedingJoinPoint proceedJob, TimeOutAop toa) throws Exception{
        ExecutorService exec = Executors.newCachedThreadPool();
        Future future = exec.submit(() -> {
            try {
                Object proceed = proceedJob.proceed();
                System.out.println("-----------------------执行成功");
                return proceed;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                return null;
            }
        });


//        return future.get(toa.timeOut(), TimeUnit.SECONDS);

        long t = System.currentTimeMillis();
        while (true) {
            Thread.sleep(500);
            if (System.currentTimeMillis() - t >= toa.timeOut() * 1000) {
                Method method = ((MethodSignature) proceedJob.getSignature()).getMethod();
                logger.error(method.toGenericString() + "----执行超时TimeOut!!!");
                // 超时之后，若原线程还在执行，则手动试图中断线程————测试发现，不会中断等待SQL执行的线程
                while(!future.isCancelled()){
                    future.cancel(true);
                }
                throw new TimeoutException("TimeOut----执行超时！");
            }
            if (future.isDone()) {
                return future.get();
            }
        }
    }

}
