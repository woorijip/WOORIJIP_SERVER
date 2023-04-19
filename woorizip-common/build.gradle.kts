val koin_version: String = "3.3.1"

plugins {
    kotlin("plugin.serialization") version "1.8.20"
}

dependencies {
    // serialization
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

    // koin
    api("io.insert-koin:koin-ktor:$koin_version")
    api("io.insert-koin:koin-logger-slf4j:$koin_version")
}
