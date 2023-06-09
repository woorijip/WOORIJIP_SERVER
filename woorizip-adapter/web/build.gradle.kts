val ktor_version: String by project
val logback_version: String by project

plugins {
    kotlin("plugin.serialization") version "1.8.20"
}

dependencies {
    // modules
    implementation(projects.woorizipCommon)
    implementation(projects.woorizipCore)
    implementation(projects.woorizipAdapter.persistence)

    // cors
    implementation("io.ktor:ktor-server-cors-jvm:$ktor_version")

    // auth
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")

    // serialization
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-jackson:$ktor_version")

    // exception handling
    implementation("io.ktor:ktor-server-status-pages-jvm:$ktor_version")

    // logging
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
}
