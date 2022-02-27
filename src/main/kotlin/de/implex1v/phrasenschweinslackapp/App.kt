package de.implex1v.phrasenschweinslackapp

import de.implex1v.phrasenschweinslackapp.client.BaseClient
import de.implex1v.phrasenschweinslackapp.client.Client
import de.implex1v.phrasenschweinslackapp.config.AppSettings
import de.implex1v.phrasenschweinslackapp.config.EnvironmentAppSettings
import de.implex1v.phrasenschweinslackapp.metrics.OnMessageMetric
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.metrics.micrometer.MicrometerMetrics
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.getKoin

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    val config: AppSettings = EnvironmentAppSettings()

    install(Koin) {
        modules(appModule)
    }

    if(config.metricsEnabled) {
        enableMetrics()
    }

    routing {
        v1()
    }
}

val appModule = module {
    single { HttpClient(CIO) }
    single { BaseClient(get(), get()) as Client }
    single { EnvironmentAppSettings() as AppSettings }
}

fun Application.enableMetrics() {
    val micrometerRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)

    install(MicrometerMetrics) {
        registry = micrometerRegistry
    }

    getKoin().loadModules(
        listOf(
            module  {
                single { micrometerRegistry as MeterRegistry }
                single { OnMessageMetric() }
            }
        )
    )

    routing {
        get("/metrics") {
            call.respond(micrometerRegistry.scrape())
        }
    }
}