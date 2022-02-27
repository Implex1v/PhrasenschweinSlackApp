package de.implex1v.phrasenschweinslackapp.metrics.base

import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.Tag

abstract class AbstractCountMetric(
    private val name: String,
    private val tags: List<Tag>
    ): Metric(), CountMetric {
    private val metric: Counter by lazy { registry.counter(name, tags) }

    override fun increment() {
        metric.increment()
    }
}