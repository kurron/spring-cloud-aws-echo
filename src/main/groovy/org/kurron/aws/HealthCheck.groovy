package org.kurron.aws

import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.stereotype.Component

import java.util.concurrent.ThreadLocalRandom

/**
 * We simulate a potentially sick service that should get retired by ECS.
 */
@Component
class HealthCheck implements HealthIndicator {

    @Override
    Health health() {
        def percent = ThreadLocalRandom.current().nextInt( 100 )
        percent < 60 ? Health.up().build() : Health.down().build()
    }
}
