val ktor_version: String by project
val koin_version: String = "3.3.1"

plugins {
    id("io.ktor.plugin") version "2.2.4"
}

application {
    mainClass.set("team.woorizip.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    // modules
    implementation(projects.woorizipAdapter.persistence)
    implementation(projects.woorizipAdapter.web)
    implementation(projects.woorizipCommon)
    implementation(projects.woorizipCore)

    // ktor
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")

    // koin
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")
}