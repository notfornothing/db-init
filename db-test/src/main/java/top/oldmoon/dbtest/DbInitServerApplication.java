package top.oldmoon.dbtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import top.oldmoon.annotation.EnableDbInit;

@SpringBootApplication
@ComponentScan({"top.oldmoon.dbinit","top.oldmoon.dbtest"})
@EnableDbInit
public class DbInitServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbInitServerApplication.class, args);
    }

}
