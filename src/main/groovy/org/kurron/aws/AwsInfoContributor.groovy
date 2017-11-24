package org.kurron.aws

import groovy.transform.Memoized
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.info.Info
import org.springframework.boot.actuate.info.InfoContributor
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.web.client.RestTemplate

/**
 * Interrogates the AWS environment and reports it back.
 */
class AwsInfoContributor implements InfoContributor {

    /**
     * The hostname of the instance.
     */
    private static String HOSTNAME_URL = 'http://169.254.169.254/latest/meta-data/hostname'

    /**
     * The availability zone the instance was launched in.
     */
    private static String AZ_URL = 'http://169.254.169.254/latest/meta-data/placement/availability-zone'

    /**
     * Uniquely identifies this container instance.
     */
    private static final CONTAINER_ID = UUID.randomUUID()

    /**
     * Handles HTTP communication.
     */
    @Autowired
    RestTemplateBuilder builder

    /**
     * Constructs a template with short timeout values.
     * @return properly configured template.
     */
    RestTemplate newTemplate() {
        builder.setConnectTimeout( 500 ).setReadTimeout( 500 ).build()
    }

    @Override
    void contribute(Info.Builder builder) {
        def hostname = queryInstanceMetaData(HOSTNAME_URL)
        def availabilityZone = queryInstanceMetaData(AZ_URL)
        def details = ['host': hostname, 'container': CONTAINER_ID, 'zone': availabilityZone]
        builder.withDetails(details)
    }

    @Memoized
    private String queryInstanceMetaData(final String url) {
        println "queryInstanceMetaData ${Calendar.instance.time} ${url}"

        final String response
        try {
            def template = newTemplate()
            response = template.getForObject(url, String)
        }
        catch (Exception ignored) {
            response = 'Not Running In AWS'
        }
        response
    }
}
