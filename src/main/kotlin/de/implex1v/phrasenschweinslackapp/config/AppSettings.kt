package de.implex1v.phrasenschweinslackapp.config

interface AppSettings {
    val groupName: String
    val baseUri: String
    val slackId: String
    val slackKey: String
    val metricsEnabled: Boolean
}