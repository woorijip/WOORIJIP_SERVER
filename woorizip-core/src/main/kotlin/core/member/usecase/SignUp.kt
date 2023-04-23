package core.member.usecase

import core.meeting.model.Category
import core.member.model.Email
import core.member.model.Member
import core.member.model.Password
import core.member.service.MemberService
import core.member.usecase.result.MemberOutput
import core.outport.TransactionPort

class SignUp(
    private val memberService: MemberService,
    private val txPort: TransactionPort
) {

    suspend operator fun invoke(input: Input): MemberOutput {
        return txPort.withNewTransaction {
            memberService.checkNotExistsEmail(input.email)
            memberService.checkNotExistsPhoneNumber(input.phoneNumber)

            val savedMember = memberService.signUp(
                input.toDomain()
            )

            input.interestCategories?.let {
                memberService.saveInterestCategories(savedMember.id, it)

                return@withNewTransaction MemberOutput.of(savedMember, it)
            }

            return@withNewTransaction MemberOutput.of(savedMember)
        }
    }

    data class Input(
        val name: String,
        val email: Email,
        val phoneNumber: String,
        val age: Int,
        val password: Password,
        val selfIntroduce: String? = null,
        val interestCategories: List<Category>? = null
    ) {
        fun toDomain(): Member {
            return Member(
                name = name,
                email = email,
                phoneNumber = phoneNumber,
                age = age,
                password = password,
                selfIntroduce = selfIntroduce
            )
        }
    }
}
