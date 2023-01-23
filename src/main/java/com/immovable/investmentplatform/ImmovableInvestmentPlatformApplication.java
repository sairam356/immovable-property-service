package com.immovable.investmentplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication(scanBasePackages = "com.immovable.investmentplatform.*")
@EnableResourceServer
public class ImmovableInvestmentPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImmovableInvestmentPlatformApplication.class, args);
	}

}
