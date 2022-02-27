package de.implex1v.phrasenschweinslackapp.client

import de.implex1v.phrasenschweinslackapp.config.EnvironmentAppSettings
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.mockk.every
import io.mockk.mockk

class BaseClientTest: DescribeSpec({
    val defaultSettings = mockk<EnvironmentAppSettings>().apply {
        every { baseUri } returns "https://phrasenschwein.org"
        every { groupName } returns "foo"
    }

    describe("BaseClientTest") {
        it("should be assignable to Client") {
            val mock = mockk<HttpClient>()
            val client = BaseClient(mock, defaultSettings)
            client.shouldBeInstanceOf<Client>()
        }

        it("should successful increase Points on user") {
            val mockEngine = MockEngine { request ->
                request.url.toString() shouldBe "https://phrasenschwein.org/foo/bar"
                request.method shouldBe HttpMethod.Get

                respond(
                    content = "",
                    status = HttpStatusCode.Created
                )
            }

            val mock = HttpClient(mockEngine)
            val client = BaseClient(mock, defaultSettings)

            client.increasePoints("bar").shouldBeTrue()
        }

        it("should fail to increase Points on user") {
            val mockEngine = MockEngine { request ->
                request.url.toString() shouldBe "https://phrasenschwein.org/foo/bar"
                request.method shouldBe HttpMethod.Get

                respond(
                    content = "",
                    status = HttpStatusCode.InternalServerError
                )
            }

            val mock = HttpClient(mockEngine)
            val client = BaseClient(mock, defaultSettings)
            mockEngine.requestHistory

            client.increasePoints("bar").shouldBeFalse()
        }
    }
})