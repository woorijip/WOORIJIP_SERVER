package core.outport

import core.member.usecase.result.TokenOutput

interface TokenPort {
    suspend fun issueToken(memberId: Long): TokenOutput
}
