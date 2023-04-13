package main.plugins

import common.exception.BaseException
import common.exception.ErrorCode.MEMBER_NOT_FOUND
import common.exception.ErrorCode.UNHANDLED_EXCEPTION
import core.member.exception.MemberNotFoundException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

fun Application.configureHandling() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            when (cause) {
                is BaseException -> {
                    call.respond(
                        message = ErrorResponse(
                            message = cause.message,
                            code = cause.errorCode.code,
//                            details = cause.details() ?: cause.messageArguments() TODO - Any 직렬화
                        ),
                        status = cause.getHttpStatusCode()
                    )
                }

                else -> {
                    call.respond(
                        message = "Internal Server Error",
                        status = HttpStatusCode.InternalServerError
                    )
                }
            }
        }
    }

    routing {
        get {
            throw MemberNotFoundException(1)
        }
    }
}

@Serializable
data class ErrorResponse(
    val message: String,
    val code: String,
    @Contextual
    val details: Any? = null
)

private fun BaseException.getHttpStatusCode(): HttpStatusCode {
    return when (this.errorCode) {
        // MEMBER
        MEMBER_NOT_FOUND -> HttpStatusCode.NotFound

        // UNHANDLED
        UNHANDLED_EXCEPTION -> HttpStatusCode.InternalServerError
    }
}
