package org.kurron.aws

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.util.UriComponentsBuilder

import java.time.Instant

/**
 * Handles REST requests.
 */
@Controller
class RestGateway {

    @RequestMapping( path = '/', method = [RequestMethod.GET], produces = ['application/json'] )
    ResponseEntity<HypermediaControl> handleGet( UriComponentsBuilder builder ) {
        def responseURL = builder.build().toUriString()
        def control = new HypermediaControl( status: HttpStatus.OK.value(), timestamp: Instant.now().toString(), path: responseURL )
        ResponseEntity.ok( control )
    }

}
