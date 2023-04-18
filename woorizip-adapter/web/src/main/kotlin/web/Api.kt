package web

import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

open class Api(private val route: Routing.() -> Unit) {
    operator fun invoke(app: Application): Routing = app.routing(route)
}

class HealthCheckApi : Api({
    get {
        call.respondText("OK")
    }
})
