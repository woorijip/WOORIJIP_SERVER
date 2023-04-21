package core.member.usecase

import core.member.model.Email
import core.member.model.Password
import core.member.service.MemberService
import core.member.usecase.result.TokenOutput
import core.outport.TokenPort
import core.outport.TransactionPort

class SignIn(
    private val memberService: MemberService,
    private val txPort: TransactionPort,
    private val tokenPort: TokenPort
) {

    suspend operator fun invoke(input: Input): TokenOutput {
        return txPort.withNewTransaction {
            val member = memberService.getMemberByEmail(input.email)
            memberService.signIn(member, input.password)

            return@withNewTransaction tokenPort.issueToken(member.id)
        }
    }

    data class Input(
        val email: Email,
        val password: Password
    )
}
