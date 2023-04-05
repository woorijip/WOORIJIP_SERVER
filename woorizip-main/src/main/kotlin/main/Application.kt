package main

import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain
import main.plugins.configureDatabases
import main.plugins.configureHTTP
import main.plugins.configureMonitoring
import main.plugins.configureRouting
import main.plugins.configureSecurity
import main.plugins.configureSerialization

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureHTTP()
    configureSecurity()
    configureSerialization()
    configureDatabases()
    configureMonitoring()
    configureRouting()
}
