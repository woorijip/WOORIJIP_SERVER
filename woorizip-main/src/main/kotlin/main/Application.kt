package main

import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain
import persistence.config.databaseConnector
import web.config.cors
import web.config.errorHandling
import web.config.logging
import web.config.serialization
import web.security.config.security

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.main() {
    binding()
    security()
    routing()
    cors()
    serialization()
    logging()
    errorHandling()
    databaseConnector()
}
