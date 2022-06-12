package top.oldmoon.dbinit.clear;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

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
public class AutoClear {

    private final ApplicationContext context;

    public AutoClear(ApplicationContext context) {
        this.context = context;
    }

    /**
     * 需要清理的bean名称集合
     */
    public static List<String> beanNameList;

    static {
        beanNameList = new ArrayList<>();
        beanNameList.add("tidyConfig");
        beanNameList.add("dbInitRunner");
        beanNameList.add("autoClear");
    }

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
            autowireCapableBeanFactory.destroyBean(beanName);
            log.info("{} has been destroyed !", beanName);
        } else if (configurableListableBeanFactory.containsBean(beanName)) {
            configurableListableBeanFactory.destroyBean(beanName);
            log.info("{} has been destroyed !", beanName);
        }
    }

    /**
     * 根据传入的bean名称集合，全部清理Spring容器中的实例
     *
     * @param beanNameList bean名称集合
     * @author DDD
     * @since 2022/5/12 17:33
     */
    public void clearAllByName(List<String> beanNameList) {
        for (String beanName : beanNameList) {
            clearByName(beanName);
        }
    }
}
