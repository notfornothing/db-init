package io.github.dingdangdog.annotation;

import io.github.dingdangdog.configure.DbInitAutoConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启动插件注解
 *
 * @author DingDangDog
 * @since 2022/7/27 11:11
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DbInitAutoConfigure.class)
public @interface EnableDbInit {
}
