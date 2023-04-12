plugins {
    kotlin("jvm") version "1.8.20"
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
}

subprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        version = "1.8.20"
    }

    apply {
        plugin("io.gitlab.arturbosch.detekt")
        version = "1.22.0"
    }

    detekt {
        toolVersion = "1.22.0"
        buildUponDefaultConfig = true
        autoCorrect = true
        config = files("$rootDir/rules/detekt.yml")
    }

    group = "team.aliens"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    dependencies {
        detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.22.0")
    }
}
