package de.implex1v.phrasenschweinslackapp.config

class EnvironmentAppSettings: AppSettings {
    override val groupName: String by lazy {
        getEnv(KEY_GROUP_NAME)
    }
    override val slackId: String by lazy {
        getEnv(KEY_SLACK_ID)
    }
    override val slackKey: String by lazy {
        getEnv(KEY_SLACK_KEY)
    }

    override val baseUri: String by lazy {
        getEnvOrDefault(KEY_BASE_URI, DEFAULT_BASE_URI)
    }

    override val metricsEnabled: Boolean by lazy {
        getEnvOrDefault(KEY_METRICS_ENABLED, DEFAULT_METRICS_ENABLED).toBoolean()
    }

    private fun getEnv(key: String): String =
        System.getenv()[key] ?: throw AppSettingsNotFound(key)

    private fun getEnvOrDefault(key: String, default: String): String =
        kotlin.runCatching { getEnv(key) }.getOrDefault(default)


    companion object {
        const val KEY_GROUP_NAME = "APP_GROUP_NAME"
        const val KEY_SLACK_ID = "APP_SLACK_ID"
        const val KEY_SLACK_KEY = "APP_SLACK_KEY"
        const val KEY_BASE_URI = "APP_BASE_URI"
        const val KEY_METRICS_ENABLED = "APP_METRICS_ENABLED"

        const val DEFAULT_BASE_URI = "https://phrasenschwein.org"
        const val DEFAULT_METRICS_ENABLED = "false"
    }
}

class AppSettingsNotFound(setting: String): RuntimeException("Environment variable '$setting' is not set")