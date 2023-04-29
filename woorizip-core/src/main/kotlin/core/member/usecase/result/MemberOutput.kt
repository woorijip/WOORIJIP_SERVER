package core.member.usecase.result

import core.meeting.model.Category
import core.member.model.Member

data class MemberOutput(
    val id: Long,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val age: Int,
    val selfIntroduce: String?,
    val interestCategoryNames: List<Category>?
) {
    companion object {

        fun of(member: Member, interestCategoryNames: List<Category>? = emptyList()) = MemberOutput(
            id = member.id,
            name = member.name,
            email = member.email.value,
            phoneNumber = member.phoneNumber,
            age = member.age,
            selfIntroduce = member.selfIntroduce,
            interestCategoryNames = interestCategoryNames
        )
    }
}
