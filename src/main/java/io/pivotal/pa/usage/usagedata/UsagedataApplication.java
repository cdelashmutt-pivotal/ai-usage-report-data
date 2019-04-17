package io.pivotal.pa.usage.usagedata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.service.PooledServiceConnectorConfig;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.cloud.service.common.RelationalServiceInfo;
import org.springframework.cloud.service.relational.DataSourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sql.DataSource;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class UsagedataApplication {

	private Logger log = LoggerFactory.getLogger(UsagedataApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(UsagedataApplication.class, args);
	}

	@Configuration
	@Profile("cloud")
	public class CloudDataSourceConfiguration {

		@Bean
		public Cloud cloud() {
			return new CloudFactory().getCloud();
		}

	}

	@Controller
	class DefaultApi {
		@RequestMapping("/")
		public String defaultVersion() {
			return "redirect:/v1";
		}
	}

}
