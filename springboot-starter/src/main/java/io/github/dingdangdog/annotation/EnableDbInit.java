package io.github.dingdangdog.annotation;

import io.github.dingdangdog.configure.DbInitAutoConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DbInitAutoConfigure.class)
public @interface EnableDbInit {
}
