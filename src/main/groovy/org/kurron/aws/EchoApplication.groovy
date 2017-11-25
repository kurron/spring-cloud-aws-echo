package org.kurron.aws

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile

@SpringBootApplication
class EchoApplication {

	static void main(String[] args) {
		SpringApplication.run EchoApplication, args
	}

	@Bean
	@Profile( ['failing-health'] )
	HealthCheck healthCheck() {
		new HealthCheck()
	}

	@Bean
	AwsInfoContributor awsInfoContributor() {
		new AwsInfoContributor()
	}
}
