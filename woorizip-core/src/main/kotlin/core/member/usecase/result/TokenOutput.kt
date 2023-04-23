package core.member.usecase.result

import java.time.LocalDateTime

data class TokenOutput(
    val accessToken: String,
    val accessTokenExpiredAt: LocalDateTime
)
