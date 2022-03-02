package de.implex1v.phrasenschweinslackapp

import de.implex1v.phrasenschweinslackapp.client.Client
import de.implex1v.phrasenschweinslackapp.config.AppSettings
import de.implex1v.phrasenschweinslackapp.config.EnvironmentAppSettings
import de.implex1v.phrasenschweinslackapp.metrics.OnMessageMetric
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.system.withEnvironment
import io.kotest.matchers.nulls.shouldNotBeNull
import io.ktor.application.Application
import io.ktor.client.HttpClient
import io.ktor.server.testing.withTestApplication
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.prometheus.PrometheusMeterRegistry
import io.mockk.mockk
import org.koin.core.KoinApplication
import org.koin.dsl.koinApplication

class AppTest: DescribeSpec({
    describe("appModule") {
        it("should contain all app dependencies") {
            withEnvironment(EnvironmentAppSettings.getTestingConfig()) {
                koinApplication {
                    modules(appModule)

                    getOrNull<HttpClient>().shouldNotBeNull()
                    getOrNull<Client>().shouldNotBeNull()
                    getOrNull<AppSettings>().shouldNotBeNull()
                }
            }
        }
    }

    describe("getMetricsModule") {
        it("should register metrics components") {
            koinApplication {
                val registry = mockk<PrometheusMeterRegistry>()
                modules(getMetricsModule(registry))

                getOrNull<MeterRegistry>()
                getOrNull<OnMessageMetric>()
            }
        }
    }
})

inline fun <reified T : Any> KoinApplication.getOrNull(): T? {
    return this.koin.getOrNull<T>()
}

fun EnvironmentAppSettings.Companion.getTestingConfig(): Map<String, String> {
    return mapOf(
        this.KEY_BASE_URI to "",
        this.KEY_GROUP_NAME to "",
        this.KEY_METRICS_ENABLED to "",
        this.KEY_SLACK_ID to "",
        this.KEY_SLACK_KEY to "",
    )
}
