package org.kurron.aws

import com.amazonaws.xray.proxies.apache.http.HttpClientBuilder
import org.springframework.boot.web.client.RestTemplateCustomizer
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

/**
 * Adjusts all REST templates that are created as beans to include X-Ray instrumentation.
 */
class TemplateCustomizer implements RestTemplateCustomizer {

    @Override
    void customize( RestTemplate restTemplate ) {
        def client = HttpClientBuilder.create().build()
        def factory = new HttpComponentsClientHttpRequestFactory( client )
        factory.setConnectTimeout( 2 )
        factory.setReadTimeout( 2 )
        restTemplate.setRequestFactory( factory )
    }
}
