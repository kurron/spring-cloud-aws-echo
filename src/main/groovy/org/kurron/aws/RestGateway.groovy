package org.kurron.aws

import groovy.transform.Memoized
import org.springframework.beans.factory.annotation.Autowired
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

    /**
     * Handles HTTP communication.
     */
    @Autowired
    RestOperations template

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

    private HypermediaControl constructPublicResponse( Optional<String> elb, Optional<Integer> port, Optional<String> endpoint, Map<String, String> headers ) {
        println "constructPublicResponse ${Calendar.instance.time}"
        // simulate a multi-service call chain by calling another instance of ourselves
        def uri = UriComponentsBuilder.newInstance().scheme( 'http' ).host( elb.get() ).port( port.get() ).path( endpoint.get() ).build().toUri()
        println "Calling ${uri}"
        def forwardingHeaders = copyIncomingHeaders( headers )
        def request = new HttpEntity<String>( forwardingHeaders )
        def response = template.exchange(uri, HttpMethod.GET, request, HypermediaControl)
        response.body
    }

    private static HttpHeaders copyIncomingHeaders( Map<String, String> headers ) {
        def forwardingHeaders = headers.inject( new HttpHeaders() ) { HttpHeaders accumulator, entry ->
            accumulator.set(entry.key, entry.value)
            accumulator
        } as HttpHeaders
        addMissingProxyHeaders( forwardingHeaders )
        forwardingHeaders
    }

    // Amazon's ELB does not add the x-forwarded-host which Spring needs to properly construct the return link so we add it ourselves
    private static void addMissingProxyHeaders( HttpHeaders forwardingHeaders ) {
        if (!forwardingHeaders.containsKey( 'x-forwarded-host' ) ) {
            def host = forwardingHeaders.getFirst( 'host' )
            forwardingHeaders.set( 'x-forwarded-host', host )
        }
    }

    private static HypermediaControl constructPrivateResponse( UriComponentsBuilder builder, Map<String, String> headers ) {
        println "constructPrivateResponse ${Calendar.instance.time}"
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
        catch ( Exception ignored ) {
            hostname = 'Not Running In AWS'
        }
        hostname
    }

}
