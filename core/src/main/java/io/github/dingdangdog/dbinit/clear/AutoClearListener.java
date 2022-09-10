package io.github.dingdangdog.dbinit.clear;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动清理容器对象
 * TODO 待验证是否有效，代码没问题，不确定是不是真的销毁且不再占用内存
 *
 * @author DDD
 * @since 2022/5/12 14:19
 */
@Slf4j
@Component
public class AutoClearListener implements ApplicationListener<AutoClearEvent> {

    private final ApplicationContext context;

    public AutoClearListener(ApplicationContext context) {
        this.context = context;
    }

//    static {
//        beanNameList = new ArrayList<>();
//        beanNameList.add("dbInitConfig");
//        beanNameList.add("dbInitRunner");
//    }

    /**
     * 根据传入的bean名称清理Spring容器中的实例
     *
     * @param beanName bean名称
     * @author DDD
     * @since 2022/5/12 17:33
     */
    public void clearByName(String beanName) {
        AutowireCapableBeanFactory autowireCapableBeanFactory = context.getAutowireCapableBeanFactory();
        ConfigurableListableBeanFactory configurableListableBeanFactory = ((ConfigurableApplicationContext) context).getBeanFactory();
        if (autowireCapableBeanFactory.containsBean(beanName)) {
            autowireCapableBeanFactory.destroyBean(context.getBean(beanName));
            log.info("--------DDD---- {} Has Been Destroyed! ----DDD--------", beanName);
        } else if (configurableListableBeanFactory.containsBean(beanName)) {
            configurableListableBeanFactory.destroyBean(context.getBean(beanName));
            log.info("--------DDD---- {} Has Been Destroyed! ----DDD--------", beanName);
        }
    }

    @Override
    public void onApplicationEvent(AutoClearEvent event) {
        String name = event.getName();
        Object source = event.getSource();
        this.clearByName(name);
    }
}
