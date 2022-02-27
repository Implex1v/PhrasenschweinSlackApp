package de.implex1v.phrasenschweinslackapp.client

interface Client {
    suspend fun increasePoints(name: String): Boolean
}