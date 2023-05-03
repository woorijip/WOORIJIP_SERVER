val koin_version: String = "3.3.1"
val jackson_version: String = "2.14.2"

plugins {
    kotlin("plugin.serialization") version "1.8.20"
}

dependencies {
    // serialization
    api("com.fasterxml.jackson.core:jackson-databind:$jackson_version")
    api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jackson_version")
    api("com.fasterxml.jackson.module:jackson-module-kotlin:$jackson_version")

    // koin
    api("io.insert-koin:koin-ktor:$koin_version")
    api("io.insert-koin:koin-logger-slf4j:$koin_version")
}
