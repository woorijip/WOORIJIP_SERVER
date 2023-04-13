package main

import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain
import main.plugins.configureHTTP
import main.plugins.configureHandling
import main.plugins.configureMonitoring
import main.plugins.configureSerialization
import persistence.config.DatabaseConnector
import persistence.config.DatabaseConnector.DB_DRIVER
import persistence.config.DatabaseConnector.DB_PASSWORD
import persistence.config.DatabaseConnector.DB_URL
import persistence.config.DatabaseConnector.DB_USER
import java.util.Properties

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureHTTP()
//    configureSecurity()
    configureSerialization()
    DatabaseConnector(Properties().apply {
        put(DB_URL, environment.config.property(DB_URL).getString())
        put(DB_USER, environment.config.property(DB_USER).getString())
        put(DB_PASSWORD, environment.config.property(DB_PASSWORD).getString())
        put(DB_DRIVER, environment.config.property(DB_DRIVER).getString())
    })
    configureMonitoring()
    configureHandling()
}
