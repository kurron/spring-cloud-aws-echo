package org.kurron.aws

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Simple data holder.
 */
class HypermediaControl {

    /**
     * HTTP status of the service, response-only.
     */
    @JsonProperty( 'status-code' )
    Integer status

    /**
     * ISO 8601 timestamp of when the service completed, response-only.
     */
    @JsonProperty( 'timestamp' )
    String timestamp

    /**
     * Relative path of the completed service, response-only.
     */
    @JsonProperty( 'calculated-return-path' )
    String path

    /**
     * Hostname that serviced the request, response-only.
     */
    @JsonProperty( 'served-by' )
    String servedBy

    /**
     * HTTP headers seen by the controller, response-only.
     */
    @JsonProperty( 'incoming-headers' )
    Map<String, String> headers
}
