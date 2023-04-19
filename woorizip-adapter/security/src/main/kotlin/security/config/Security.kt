package security.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import common.exception.BaseErrorCode
import common.exception.ErrorResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.response.respond
import security.token.TokenAdapter.Companion.JWT_MEMBER_ID
import kotlin.properties.Delegates

fun Application.security() {
    SecurityProperties(environment.config)

    authentication {
        jwt {
            verifier(
                JWT
                    .require(Algorithm.HMAC256(SecurityProperties.secret))
                    .withAudience(SecurityProperties.audience)
                    .withIssuer(SecurityProperties.issuer)
                    .build()
            )

            validate { credential ->
                if (credential.payload.getClaim(JWT_MEMBER_ID).asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }

            challenge { _, _ ->
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

object SecurityProperties {
    private const val millisecondPerSecond: Long = 1000

    lateinit var audience: String
    lateinit var secret: String
    lateinit var issuer: String
    var accessExpired: Long by Delegates.notNull()
        private set

    operator fun invoke(config: ApplicationConfig) {
        val prefix = "jwt"
        audience = config.property("$prefix.audience").getString()
        secret = config.property("$prefix.secret").getString()
        issuer = config.property("$prefix.issuer").getString()
        accessExpired = config.property("$prefix.expired.access-token").getString().toLong() * millisecondPerSecond
    }
}
