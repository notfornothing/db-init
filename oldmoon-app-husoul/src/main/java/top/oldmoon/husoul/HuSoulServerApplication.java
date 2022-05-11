package top.oldmoon.husoul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"oldmoon.api", "top.oldmoon.husoul", "top.oldmoon"})
public class HuSoulServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HuSoulServerApplication.class, args);
    }

}
