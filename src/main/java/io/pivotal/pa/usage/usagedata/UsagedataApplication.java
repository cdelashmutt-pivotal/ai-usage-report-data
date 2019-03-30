package io.pivotal.pa.usage.usagedata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.sql.DataSource;

@SpringBootApplication
public class UsagedataApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsagedataApplication.class, args);
	}

	@Bean
	@Profile("!cloud")
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
			.setType(EmbeddedDatabaseType.H2)
			.setName("testdb;MODE=MySQL")
			.build();
	}

	@Controller
	class DefaultApi {
		@RequestMapping("/")
		public String defaultVersion() {
			return "redirect:/v1";
		}
	}

}
