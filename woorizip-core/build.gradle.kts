val koin_version: String = "3.3.1"

dependencies {
    // modules
    implementation(projects.woorizipCommon)

    // koin
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")
}
