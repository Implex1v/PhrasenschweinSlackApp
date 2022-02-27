package de.implex1v.phrasenschweinslackapp.config

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.system.withEnvironment
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith
internal class EnvironmentAppSettingsTest: DescribeSpec({
    describe("EnvironmentAppSettingsTest") {
        it("should be assignable to AppSettings") {
            EnvironmentAppSettings().shouldBeInstanceOf<AppSettings>()
        }

        it("should return AppSettings depending on env vars") {
            withEnvironment(
                mapOf(
                    EnvironmentAppSettings.KEY_GROUP_NAME to "foo",
                    EnvironmentAppSettings.KEY_SLACK_ID to "bar",
                    EnvironmentAppSettings.KEY_SLACK_KEY to "baz",
                    EnvironmentAppSettings.KEY_BASE_URI to "bof",
                )
            ) {
                val settings = EnvironmentAppSettings()
                settings.groupName shouldBe "foo"
                settings.slackId shouldBe "bar"
                settings.slackKey shouldBe "baz"
                settings.baseUri shouldBe "bof"
            }
        }

        it("should throw if AppSetting is missing") {
            val ex = shouldThrow<AppSettingsNotFound> {
                EnvironmentAppSettings().slackKey
            }

            ex.message shouldContain EnvironmentAppSettings.KEY_SLACK_KEY
        }
    }
})