package io.pivotal.pa.usage.usagedata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class UsagedataApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsagedataApplication.class, args);
	}

	@Controller
	class DefaultApi {
		@RequestMapping("/")
		public String defaultVersion() {
			return "redirect:/v1";
		}
	}
}
