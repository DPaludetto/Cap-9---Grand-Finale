package br.com.palutec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = { "br.com.palutec", "br.com.squada"})
@EnableJpaRepositories(basePackages = { "br.com.palutec" })
@EntityScan(basePackages = { "br.com.palutec"	 })
@EnableScheduling
@EnableAsync
public class Application {

	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
}
