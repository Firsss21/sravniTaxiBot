package firsov.study.sravnitaxibot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SravniTaxiBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SravniTaxiBotApplication.class, args);
    }

}
