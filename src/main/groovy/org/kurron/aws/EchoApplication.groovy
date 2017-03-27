package org.kurron.aws

import com.amazonaws.xray.AWSXRay
import com.amazonaws.xray.AWSXRayRecorderBuilder
import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter
import com.amazonaws.xray.plugins.EC2Plugin
import com.amazonaws.xray.plugins.ECSPlugin
import com.amazonaws.xray.strategy.sampling.LocalizedSamplingStrategy
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.web.client.RestTemplate

import javax.servlet.Filter

@SpringBootApplication
class EchoApplication {

	static {
		def builder = AWSXRayRecorderBuilder.standard()
				                            .withPlugin( new EC2Plugin() )
				                            .withPlugin( new ECSPlugin() )

		def ruleFile = EchoApplication.getResource( '/sampling-rules.yml' )
		println "ruleFile = ${ruleFile}"
		builder.withSamplingStrategy( new LocalizedSamplingStrategy( ruleFile ) )
		AWSXRay.setGlobalRecorder( builder.build() )
	}

	static void main(String[] args) {
		SpringApplication.run EchoApplication, args
	}

	@Bean
	@Profile( ['x-ray'] )
	Filter TracingFilter() {
		new AWSXRayServletFilter( 'echo' )
	}

    @Bean
	@Profile( ['x-ray'] )
    TemplateCustomizer templateCustomizer() {
        new TemplateCustomizer()
    }

	@Bean
	HealthCheck healthCheck() {
		new HealthCheck()
	}

    @Bean
    RestTemplate restTemplate( RestTemplateBuilder builder ) {
        builder.build()
    }
}
