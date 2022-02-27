package de.implex1v.phrasenschweinslackapp

import de.implex1v.phrasenschweinslackapp.config.AppSettings
import de.implex1v.phrasenschweinslackapp.metrics.OnMessageMetric
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route
import org.koin.ktor.ext.inject

fun Routing.v1() {
    val config = inject<AppSettings>().value

    route("/v1") {
        get("/messages") {
            if(config.metricsEnabled) {
                this@v1.inject<OnMessageMetric>().value.increment()
            }

            call.respond("OK")
        }
    }
}