val koin_version: String = "3.3.1"

plugins {
    kotlin("plugin.serialization") version "1.8.20"
}

dependencies {
    // serialization
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    api("com.fasterxml.jackson.core:jackson-databind:2.14.2")
    api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.2")

    // koin
    api("io.insert-koin:koin-ktor:$koin_version")
    api("io.insert-koin:koin-logger-slf4j:$koin_version")
}
