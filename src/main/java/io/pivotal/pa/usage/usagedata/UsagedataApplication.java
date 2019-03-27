package io.pivotal.pa.usage.usagedata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories
public class UsagedataApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsagedataApplication.class, args);
	}

}
