package org.kurron.aws

import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component

import java.util.concurrent.atomic.AtomicInteger

/**
 * We simulate a potentially sick service that should get retired by ECS.
 */
@Component
class HealthCheck implements HealthIndicator {

    /**
     * We'll use this to control health or sickness.
     */
    private final AtomicInteger visits = new AtomicInteger( 0 )

    /**
     * Repeating sequence of health statuses.
     */
    private final String[] sequence = ['up', 'up', 'up', 'down', 'down', 'down']

    @Override
    Health health() {
        def status = sequence[visits.incrementAndGet() % sequence.length]
        'up' == status ? Health.up().build() : Health.down().build()
    }
}
