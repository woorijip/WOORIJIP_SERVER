package core.member.usecase

import core.member.model.Email
import core.member.model.InterestCategory
import core.member.model.Member
import core.member.model.Password
import core.member.service.MemberService
import core.member.usecase.result.MemberOutput
import kotlinx.serialization.Serializable

class SignUp(private val memberService: MemberService) {

    suspend operator fun invoke(input: Input): MemberOutput {
        memberService.checkNotExistsEmail(input.email)
        memberService.checkNotExistsPhoneNumber(input.phoneNumber)

        val savedMember = memberService.saveMember(
            input.toDomain()
        )

        input.interestCategories?.let {
            val interestCategories = input.interestCategories.map { InterestCategory(it, savedMember.id) }
            memberService.saveInterestCategories(savedMember.id, interestCategories)

            return MemberOutput(savedMember, input.interestCategories)
        }

        return MemberOutput(savedMember)
    }

    @Serializable
    data class Input(
        val name: String,
        val email: Email,
        val phoneNumber: String,
        val age: Int,
        val password: Password,
        val selfIntroduce: String? = null,
        val interestCategories: List<String>? = null
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
