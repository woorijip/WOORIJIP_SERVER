val ktor_version: String by project

plugins {
    id("io.ktor.plugin") version "2.2.4"
}

dependencies {
    // modules
    implementation(projects.woorizipCommon)
    implementation(projects.woorizipCore)

    // auth
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")
}
