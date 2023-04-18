package main

import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain
import persistence.config.DatabaseConnector
import persistence.config.DatabaseConnector.DB_DRIVER
import persistence.config.DatabaseConnector.DB_PASSWORD
import persistence.config.DatabaseConnector.DB_URL
import persistence.config.DatabaseConnector.DB_USER
import web.config.cors
import web.config.errorHandling
import web.config.logging
import web.config.serialization
import java.util.Properties

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.main() {
    binding()
    routing()
    cors()
    serialization()
    logging()
    errorHandling()

    DatabaseConnector(
        Properties().apply {
            put(DB_URL, environment.config.property(DB_URL).getString())
            put(DB_USER, environment.config.property(DB_USER).getString())
            put(DB_PASSWORD, environment.config.property(DB_PASSWORD).getString())
            put(DB_DRIVER, environment.config.property(DB_DRIVER).getString())
        }
    )
}
