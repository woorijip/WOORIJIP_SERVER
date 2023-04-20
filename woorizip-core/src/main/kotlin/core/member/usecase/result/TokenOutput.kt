package core.member.usecase.result

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class TokenOutput(
    val accessToken: String,
    @Contextual
    val accessTokenExpiredAt: LocalDateTime
)
