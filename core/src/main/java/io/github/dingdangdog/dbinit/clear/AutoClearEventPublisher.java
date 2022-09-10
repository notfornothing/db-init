package io.github.dingdangdog.dbinit.clear;

import org.springframework.context.ApplicationEventPublisher;

/**
 * 事件发布器
 *
 * @author DingDangDog
 * @since 2022/9/10 14:39
 */
public class AutoClearEventPublisher {
    private final ApplicationEventPublisher publisher;

    public AutoClearEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishAutoClearEvent(String name, Object obj){
        publisher.publishEvent(new AutoClearEvent(name, obj));
    }
}
