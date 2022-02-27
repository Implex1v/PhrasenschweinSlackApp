package de.implex1v.phrasenschweinslackapp.metrics

import de.implex1v.phrasenschweinslackapp.metrics.base.AbstractCountMetric
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.micrometer.core.instrument.Counter
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Tag
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

internal class OnMessageMetricTest: KoinTest, DescribeSpec() {
    val meter by inject<OnMessageMetric>()

    init {
        describe("OnMessageMetricTest should") {
            it("increment counter") {
                val registry = mockk<MeterRegistry>()
                val nameCapture = slot<String>()
                val tagsCapture = slot<Iterable<Tag>>()

                val counter = mockk<Counter>()

                justRun { counter.increment() }
                every { registry.counter(capture(nameCapture), capture(tagsCapture)) } returns counter

                startKoin {
                    modules(
                        module {
                            single { OnMessageMetric() }
                            single { registry }
                        }
                    )
                }

                meter.increment()

                meter.shouldBeInstanceOf<AbstractCountMetric>()
                nameCapture.isCaptured shouldBe true
                nameCapture.captured shouldBe "ps_slack"
            }
        }
    }
}
