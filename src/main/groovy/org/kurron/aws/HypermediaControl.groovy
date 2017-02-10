package org.kurron.aws

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Simple data holder.
 */
class HypermediaControl {

    /**
     * HTTP status of the service, response-only.
     */
    @JsonProperty( 'status' )
    Integer status

    /**
     * ISO 8601 timestamp of when the service completed, response-only.
     */
    @JsonProperty( 'timestamp' )
    String timestamp

    /**
     * Relative path of the completed service, response-only.
     */
    @JsonProperty( 'path' )
    String path
}
