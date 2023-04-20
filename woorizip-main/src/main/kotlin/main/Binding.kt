package main

import core.coreModule
import io.ktor.server.application.Application
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import org.koin.logger.slf4jLogger
import persistence.persistenceModule
import web.webModule

fun Application.binding() {
    stopKoin()
    startKoin {
        slf4jLogger(level = Level.INFO)
        modules(
            coreModule,
            persistenceModule,
            webModule
        )
    }
}
