plugins {
    jacoco
    application
    kotlin("jvm") version "1.6.10"
    id("org.sonarqube") version "3.3"
}

group = "de.implex1v"
version = "0.1.0"

repositories {
    mavenCentral()
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    reports {
        xml.required.set(true)
        csv.required.set(false)
    }
}

sonarqube {
    properties {
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.organization", "implex1v")
        property("sonar.projectKey", "Implex1v_PhrasenschweinSlackApp")
        property("sonar.login", System.getProperty("sonar.login") ?: System.getenv("SONAR_TOKEN")!!)
    }
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

val ktor_version = "1.6.7"
val koin_version = "3.1.5"
val kotest_version = "5.1.0"
val prometheus_version = "1.8.3"
val logback_version = "1.2.10"
val logback_contribution = "0.1.5"
val jackson_version = "2.13.1"

dependencies {
    implementation(kotlin("stdlib"))

    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-metrics-micrometer:$ktor_version")
    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.micrometer:micrometer-registry-prometheus:$prometheus_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("ch.qos.logback.contrib:logback-jackson:$logback_contribution")
    implementation("ch.qos.logback.contrib:logback-json-classic:$logback_contribution")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jackson_version")

    testImplementation("io.mockk:mockk:1.12.2")
    testImplementation("io.insert-koin:koin-test:$koin_version")
    testImplementation("io.ktor:ktor-client-mock:$ktor_version")
    testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotest_version")
    testImplementation("io.kotest:kotest-assertions-core:$kotest_version")
    testImplementation("io.kotest.extensions:kotest-extensions-koin:1.1.0")
}