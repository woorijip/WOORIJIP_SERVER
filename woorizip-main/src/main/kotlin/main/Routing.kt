package main

import io.ktor.server.application.Application
import org.koin.java.KoinJavaComponent
import web.Api

fun Application.routing() {
    KoinJavaComponent.getKoin().getAll<Api>().forEach() {
        it(this)
    }
}
