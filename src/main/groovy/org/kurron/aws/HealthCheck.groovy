package org.kurron.aws

import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator

import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.atomic.AtomicInteger

/**
 * We simulate a potentially sick service that should get retired by ECS.
 */
class HealthCheck implements HealthIndicator {

    /**
     * We'll use this to control health or sickness.
     */
    private final AtomicInteger visits = new AtomicInteger( 0 )

    /**
     * How many visits before we start reporting as unhealthy.
     */
    private static final MAX_VISITS = ThreadLocalRandom.current().nextInt( 100, 250 )

    @Override
    Health health() {
        visits.getAndIncrement() < MAX_VISITS ? Health.up().build() : Health.down().build()
    }
}
