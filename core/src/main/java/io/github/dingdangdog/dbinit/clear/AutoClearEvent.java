package io.github.dingdangdog.dbinit.clear;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 自动清理事件
 *
 * @author DingDangDog
 * @since 2022/9/10 13:46
 */
public class AutoClearEvent extends ApplicationEvent {
    @Getter
    private final String name;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source   the object on which the event initially occurred or with
     *                 which the event is associated (never {@code null})
     * @param beanName source beanName
     */
    public AutoClearEvent(String beanName, Object source) {
        super(source);
        this.name = beanName;
    }
}
