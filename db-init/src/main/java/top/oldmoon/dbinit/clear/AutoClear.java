package top.oldmoon.dbinit.clear;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import top.oldmoon.dbinit.config.TidyConfig;
import top.oldmoon.dbinit.runner.DbInitRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 自动清理容器对象
 *
 * @author hupg
 * @date 2022/5/12 14:19
 */
@Component
@Slf4j
public class AutoClear {

    @Resource
    private ApplicationContext context;

    public static List<String> beanNameList;

    static {
        beanNameList = new ArrayList<>();
        beanNameList.add("tidyConfig");
        beanNameList.add("dbInitRunner");
        beanNameList.add("autoClear");
    }

    public void clearByName(String beanName) {
        AutowireCapableBeanFactory autowireCapableBeanFactory = context.getAutowireCapableBeanFactory();
        ConfigurableListableBeanFactory configurableListableBeanFactory = ((ConfigurableApplicationContext) context).getBeanFactory();
        if (autowireCapableBeanFactory.containsBean(beanName)) {
            autowireCapableBeanFactory.destroyBean(beanName);
            log.info("{} has been destroyed !", beanName);
        } else if (configurableListableBeanFactory.containsBean(beanName)) {
            configurableListableBeanFactory.destroyBean(beanName);
            log.info("{} has been destroyed !", beanName);
        }
    }

    public void clearAllByName(List<String> beanNameList) {
        for (String beanName : beanNameList) {
            clearByName(beanName);
        }
    }
}
