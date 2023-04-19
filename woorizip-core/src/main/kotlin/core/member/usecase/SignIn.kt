package core.member.usecase

import core.member.model.Email
import core.member.model.Password
import core.member.service.MemberService
import core.member.usecase.result.TokenOutput
import core.outport.TransactionPort
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

class SignIn(
    private val memberService: MemberService,
    private val txPort: TransactionPort
) {

    suspend operator fun invoke(input: Input): TokenOutput {
        return txPort.withNewTransaction {
            val member = memberService.getMemberByEmail(input.email)
            memberService.signIn(member, input.password)

            // 토큰 발급

            return@withNewTransaction TokenOutput("", LocalDateTime.now())
        }
    }

    @Serializable
    data class Input(
        val email: Email,
        val password: Password
    )
}