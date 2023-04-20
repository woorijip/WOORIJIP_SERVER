package main

import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain
import persistence.config.databaseConnector
import security.config.security
import web.config.cors
import web.config.errorHandling
import web.config.logging
import web.config.serialization

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.main() {
    binding()
    routing()
    cors()
    serialization()
    logging()
    errorHandling()
    security()
    databaseConnector()
}
