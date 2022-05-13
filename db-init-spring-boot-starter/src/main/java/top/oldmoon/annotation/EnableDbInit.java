package top.oldmoon.annotation;

import org.springframework.context.annotation.Import;
import top.oldmoon.configure.DbInitAutoConfigure;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DbInitAutoConfigure.class)
public @interface EnableDbInit {
}
