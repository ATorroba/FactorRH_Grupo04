package es.upm.dit.isst.tfgapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringBootReactApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootReactApplication.class, args);
    }
}
