plugins {
    kotlin("jvm") version "1.6.10"
}

group = "de.implex1v"
version = "0.1.0"

repositories {
    mavenCentral()
}

var ktorVersion = "2.0.0-beta-1"

dependencies {
    implementation(kotlin("stdlib"))

    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-status-pages-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-default-headers-jvm:$ktorVersion")

    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}