val exposed_version: String by project
val mysql_version: String = "8.0.32"
val hikari_version: String = "5.0.1"

dependencies {
    implementation(projects.woorizipCommon)
    implementation(projects.woorizipCore)

    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposed_version")
    implementation("com.mysql:mysql-connector-j:$mysql_version")
    implementation("com.zaxxer:HikariCP:$hikari_version")
}
