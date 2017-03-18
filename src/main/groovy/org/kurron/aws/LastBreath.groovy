package org.kurron.aws

import org.springframework.boot.ExitCodeGenerator

/**
 * Called just prior to exiting the application.
 */
class LastBreath implements ExitCodeGenerator {

    @Override
    int getExitCode() {
        println( 'Exiting application' )
        0
    }
}
