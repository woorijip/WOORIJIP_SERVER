package main.plugins

import common.exception.BaseErrorCode
import common.exception.BaseException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import kotlinx.serialization.Serializable

fun Application.configureHandling() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            when (cause) {
                is BaseException -> {
                    call.respond(
                        message = ErrorResponse(
                            code = cause.code,
                            message = cause.message,
                            arguments = cause.messageArguments()
                        ),
                        status = cause.getHttpStatusCode()
                    )
                }

                else -> {
                    call.respond(
                        message = ErrorResponse(
                            code = BaseErrorCode.UNHANDLED_EXCEPTION.code,
                            message = BaseErrorCode.UNHANDLED_EXCEPTION.message
                        ),
                        status = HttpStatusCode.InternalServerError
                    )
                }
            }
        }
    }
}

@Serializable
data class ErrorResponse(
    val code: String,
    val message: String,
    val arguments: Collection<String>? = null
)

private fun BaseException.getHttpStatusCode(): HttpStatusCode {
    return when (this) {
        is BaseException.BadRequestException -> HttpStatusCode.BadRequest
        is BaseException.UnauthorizedException -> HttpStatusCode.Unauthorized
        is BaseException.ForbiddenException -> HttpStatusCode.Forbidden
        is BaseException.NotFoundException -> HttpStatusCode.NotFound
        is BaseException.ConflictException -> HttpStatusCode.Conflict

        else -> HttpStatusCode.InternalServerError
    }
}
