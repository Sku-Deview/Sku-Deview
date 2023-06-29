package kr.co.skudeview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SkuDeviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkuDeviewApplication.class, args);
    }

}
