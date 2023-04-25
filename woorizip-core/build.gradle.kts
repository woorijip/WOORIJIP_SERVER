val kotest_version: String = "5.5.5"

plugins {
    kotlin("plugin.serialization") version "1.8.20"
}

dependencies {
    // modules
    implementation(projects.woorizipCommon)

    // bcrypt
    implementation("org.mindrot:jbcrypt:0.4")

    // test
    testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotest_version")
    testImplementation("io.mockk:mockk:1.13.4")
}
