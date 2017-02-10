package org.kurron.aws

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.HandlerMapping

import javax.servlet.http.HttpServletRequest
import java.time.Instant

/**
 * Handles REST requests.
 */
@Controller
class RestGateway {

    @RequestMapping( path = '/', method = [RequestMethod.GET], produces = ['application/json'] )
    ResponseEntity<HypermediaControl> handleGet( HttpServletRequest request ) {
        def path = request.getAttribute( HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE ) as String
        def control = new HypermediaControl( status: HttpStatus.OK.value(), timestamp: Instant.now().toString(), path: path )
        ResponseEntity.ok( control )
    }

}
