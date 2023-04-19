plugins {
    kotlin("plugin.serialization") version "1.8.20"
}

dependencies {
    // modules
    implementation(projects.woorizipCommon)

    // bcrypt
    implementation("org.mindrot:jbcrypt:0.4")
}
