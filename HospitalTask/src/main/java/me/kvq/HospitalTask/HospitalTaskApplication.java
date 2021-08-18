package me.kvq.HospitalTask;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HospitalTaskApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(HospitalTaskApplication.class, args);
	}
	
}
