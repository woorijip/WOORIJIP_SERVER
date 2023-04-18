package core.member.usecase

import core.member.model.Email
import core.member.model.Member
import core.member.model.Password
import core.member.service.MemberService
import core.member.usecase.result.MemberOutput

class SignUp(private val memberService: MemberService) {

    suspend operator fun invoke(input: Input): MemberOutput {
        // 유효성 검증 로직 추가

        val savedMember = memberService.save(
            input.toDomain()
        )

        return MemberOutput(savedMember)
    }

    data class Input(
        val name: String,
        val email: Email,
        val phoneNumber: String,
        val age: Int,
        val password: Password,
        val interestCategories: List<String>,
        val selfIntroduce: String?
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