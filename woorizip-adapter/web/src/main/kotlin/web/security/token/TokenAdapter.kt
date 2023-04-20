package web.security.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import core.member.usecase.result.TokenOutput
import core.outport.TokenPort
import web.security.config.SecurityProperties
import java.time.LocalDateTime
import java.util.Date

class TokenAdapter : TokenPort {
    override suspend fun issueToken(memberId: Int): TokenOutput {
        val accessToken = issueAccessToken(memberId)
        val accessTokenExpiredAt = LocalDateTime.now().plusSeconds(SecurityProperties.accessExpired)

        return TokenOutput(
            accessToken = accessToken,
            accessTokenExpiredAt = accessTokenExpiredAt
        )
    }

    private fun issueAccessToken(memberId: Int): String {
        return JWT.create()
            .withHeader(mapOf("JWT" to "access"))
            .withAudience(SecurityProperties.audience)
            .withIssuer(SecurityProperties.issuer)
            .withClaim(Claims.JWT_MEMBER_ID, memberId.toString())
            .withExpiresAt(Date(System.currentTimeMillis() + SecurityProperties.accessExpired * millisecondPerSecond))
            .sign(Algorithm.HMAC256(SecurityProperties.secret))
    }

    companion object {
        private const val millisecondPerSecond: Long = 1000
    }
}

object Claims {
    const val JWT_MEMBER_ID: String = "MID"
}
