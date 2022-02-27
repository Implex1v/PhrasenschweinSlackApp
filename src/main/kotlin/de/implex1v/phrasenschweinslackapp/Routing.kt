package de.implex1v.phrasenschweinslackapp

import de.implex1v.phrasenschweinslackapp.metrics.OnMessageMetric
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route
import org.koin.ktor.ext.inject

fun Routing.v1() {
    val onMessageMetric = injectOrNull<OnMessageMetric>()

    route("/v1") {
        get("/messages") {
            onMessageMetric?.increment()

            call.respond("OK")
        }
    }
}

/**
 * Returns instance of [T] or null if no instance was found.
 * @return Injected instance of [T] or null
 */
inline fun <reified T: Any> Routing.injectOrNull(): T? {
    return kotlin.runCatching { inject<T>().value }.getOrNull()
}