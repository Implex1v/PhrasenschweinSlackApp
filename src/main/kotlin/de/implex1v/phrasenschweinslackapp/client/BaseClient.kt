package de.implex1v.phrasenschweinslackapp.client

import de.implex1v.phrasenschweinslackapp.config.AppSettings
import io.ktor.client.HttpClient
import io.ktor.client.features.ResponseException
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

class BaseClient(
    private val client: HttpClient,
    settings: AppSettings,
) : Client {
    private val baseUri: String
    private val groupName: String

    init {
        baseUri = settings.baseUri
        groupName = settings.groupName
    }

    override suspend fun increasePoints(name: String): Boolean {
        return try {
            val response = client.get<HttpResponse>("$baseUri/$groupName/$name")
            response.status.isSuccess()
        } catch (ex: ResponseException) {
            false
        }
    }
}