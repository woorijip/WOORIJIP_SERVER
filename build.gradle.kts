import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.nio.file.Files
import java.nio.file.StandardCopyOption

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

    tasks.create("installGitHooks") {
        doLast {
            val gitDir = file(".git")
            val prePushFile = file(".githooks/pre-push")

            if (!gitDir.exists() || !prePushFile.exists()) {
                return@doLast
            }

            Files.copy(
                File(".githooks/pre-push").toPath(),
                File(".git/hooks/pre-push").toPath(),
                StandardCopyOption.REPLACE_EXISTING,
            )
        }
    }

    tasks.build {
        dependsOn("installGitHooks")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "17"
        }
        dependsOn("installGitHooks")
    }
}
