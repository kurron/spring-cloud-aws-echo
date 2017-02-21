package org.kurron.aws

import groovy.transform.Memoized
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.*
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.client.RestOperations
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

import java.time.Instant

/**
 * Handles REST requests.
 */
@SuppressWarnings( 'GroovyUnusedDeclaration' )
@Controller
class RestGateway {

    /**
     * The magic ip address that EC2 instance meta-data can be obtained from.
     */
    private static String URL = 'http://169.254.169.254/latest/meta-data/hostname'

    private final RestOperations theTemplate = new RestTemplateBuilder().setConnectTimeout( 1 ).setReadTimeout( 1 ).build()

    @GetMapping( path = '/', produces = ['application/json'] )
    ResponseEntity<HypermediaControl> echoInstanceInformation( UriComponentsBuilder builder,
                                                               @RequestHeader Map<String, String> headers,
                                                               @RequestParam( required = false ) Optional<String> elb,
                                                               @RequestParam( required = false ) Optional<Integer> port,
                                                               @RequestParam( required = false ) Optional<String> endpoint ) {

        // ugly but I want to do this quickly
        final HypermediaControl control
        if ( elb.present && port.present && endpoint.present ) {
            control = constructPublicResponse( elb, port, endpoint, headers )
        }
        else {
            control = constructPrivateResponse( builder, headers )
        }
        ResponseEntity.ok( control )
    }

    private static HypermediaControl constructPublicResponse(Optional<String> elb, Optional<Integer> port, Optional<String> endpoint, Map<String, String> headers) {
        def uri = UriComponentsBuilder.newInstance().scheme( 'http' ).host( elb.get() ).port( port.get() ).path( endpoint.get() ).build().toUri()
        def template = RestTemplateBuilder.newInstance().build()
        def httpHeaders = headers.inject( new HttpHeaders() ) { HttpHeaders accumulator, entry ->
            // Spring does not respect the x-forwarded-for header so we need to map it to x-forwarded-host
            accumulator.add( entry.key == 'x-forwarded-for' ? 'x-forwarded-host' : entry.key, entry.value )
            accumulator
        } as HttpHeaders
        def request = new HttpEntity<String>( httpHeaders )
        def response = template.exchange(uri, HttpMethod.GET, request, HypermediaControl)
        response.body
    }

    private static HypermediaControl constructPrivateResponse( UriComponentsBuilder builder, Map<String, String> headers ) {
        def hostname = determineHostName( URL )
        def responseURL = builder.build().toUriString()
        new HypermediaControl( status: HttpStatus.OK.value(),
                               timestamp: Instant.now().toString(),
                               path: responseURL,
                               servedBy: hostname,
                               headers: headers )
    }

    @Memoized
    private static String determineHostName( final String url ) {
        final String hostname
        try {
            hostname = new RestTemplate().getForObject( url, String )
        }
        catch ( Exception ignored) {
            hostname = 'Not Running In AWS'
        }
        hostname
    }

}
