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
            val createdMember = memberService.createMember(
                input.toDomain()
            )

            input.interestCategories?.let {
                memberService.createInterestCategories(createdMember.id, it)

                return@withNewTransaction MemberOutput.of(createdMember, it)
            }

            return@withNewTransaction MemberOutput.of(createdMember)
        }
    }

    data class Input(
        val name: String,
        val email: String, // TODO value class로 변경
        val phoneNumber: String,
        val age: Int,
        val password: String, // TODO value class로 변경
        val selfIntroduce: String? = null,
        val interestCategories: List<Category>? = null
    ) {
        fun toDomain(): Member {
            return Member(
                name = name,
                email = Email(email),
                phoneNumber = phoneNumber,
                age = age,
                password = Password(password),
                selfIntroduce = selfIntroduce
            )
        }
    }
}
