package de.implex1v.phrasenschweinslackapp.metrics

import de.implex1v.phrasenschweinslackapp.metrics.base.AbstractCountMetric
import io.micrometer.core.instrument.Tag

class OnMessageMetric: AbstractCountMetric(
    "ps_slack",
    listOf(
        Tag.of("endpoint", "onMessage")
    )
)