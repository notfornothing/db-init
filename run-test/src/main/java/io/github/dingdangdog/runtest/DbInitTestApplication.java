package io.github.dingdangdog.runtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.dingdangdog.annotation.EnableDbInit;

@SpringBootApplication(scanBasePackages = "io.github.dingdangdog")
@EnableDbInit
public class DbInitTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbInitTestApplication.class, args);
    }

}
