package security.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import core.member.usecase.result.TokenOutput
import core.outport.TokenPort
import security.config.SecurityProperties
import java.time.LocalDateTime
import java.util.Date

class TokenAdapter : TokenPort {
    override suspend fun generateToken(memberId: Int): TokenOutput {
        val accessToken = generateAccessToken(memberId)
        val accessTokenExpiredAt = LocalDateTime.now().plusNanos(SecurityProperties.accessExpired)

        return TokenOutput(
            accessToken = accessToken,
            accessTokenExpiredAt = accessTokenExpiredAt
        )
    }

    private fun generateAccessToken(memberId: Int): String {
        return JWT.create()
            .withSubject(JWT_SUBJECT)
            .withJWTId(JWT_ID_ACCESS)
            .withAudience(SecurityProperties.audience)
            .withIssuer(SecurityProperties.issuer)
            .withClaim(JWT_MEMBER_ID, memberId.toString())
            .withExpiresAt(Date(System.currentTimeMillis() + SecurityProperties.accessExpired))
            .sign(Algorithm.HMAC256(SecurityProperties.secret))
    }

    companion object {
        const val JWT_SUBJECT: String = "Authentication"
        const val JWT_ID_ACCESS: String = "Access"
        const val JWT_MEMBER_ID: String = "MemberId"
    }
}
