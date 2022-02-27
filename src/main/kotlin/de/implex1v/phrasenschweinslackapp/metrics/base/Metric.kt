package de.implex1v.phrasenschweinslackapp.metrics.base

import io.micrometer.core.instrument.MeterRegistry
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class Metric: KoinComponent {
    protected val registry by inject<MeterRegistry>()
}