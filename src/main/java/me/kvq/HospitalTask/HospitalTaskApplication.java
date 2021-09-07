package me.kvq.HospitalTask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@Configuration
@EnableWebMvc
public class HospitalTaskApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(HospitalTaskApplication.class, args);
    }

}