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
        ThreadLocalRandom.current().nextBoolean() ? Health.up().build() : Health.down().build()
    }
}
