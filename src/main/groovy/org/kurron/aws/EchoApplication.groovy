package org.kurron.aws

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class EchoApplication {

	static void main(String[] args) {
		SpringApplication.run EchoApplication, args
	}

	@Bean
	HealthCheck healthCheck() {
		new HealthCheck()
	}
}
