package me.kvq.hospitaltask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class HospitalTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalTaskApplication.class, args);
    }

}
