package core.outport

import core.member.usecase.result.TokenOutput

interface TokenPort {
    suspend fun generateToken(memberId: Int): TokenOutput
}
