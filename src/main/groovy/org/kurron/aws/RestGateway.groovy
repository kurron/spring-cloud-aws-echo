package org.kurron.aws

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

import java.time.Instant

/**
 * Handles REST requests.
 */
@Controller
class RestGateway {

    private static String URL = 'http://169.254.169.254/latest/meta-data/hostname'

    @RequestMapping( path = '/', method = [RequestMethod.GET], produces = ['application/json'] )
    ResponseEntity<HypermediaControl> handleGet( UriComponentsBuilder builder ) {

        String hostname = determineHostName()

        def responseURL = builder.build().toUriString()
        def control = new HypermediaControl( status: HttpStatus.OK.value(),
                                             timestamp: Instant.now().toString(),
                                             path: responseURL,
                                             servedBy: hostname )
        ResponseEntity.ok( control )
    }

    private static String determineHostName() {
        final String hostname
        try {
            hostname = new RestTemplate().getForObject( URL, String )
        } catch ( Exception e ) {
            hostname = 'Not Running In AWS'
        }
        hostname
    }

}
