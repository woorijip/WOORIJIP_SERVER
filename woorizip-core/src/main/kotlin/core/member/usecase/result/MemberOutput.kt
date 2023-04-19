package core.member.usecase.result

import core.member.model.Member
import kotlinx.serialization.Serializable

@Serializable
data class MemberOutput(
    val id: Int,
    val name: String,
    val email: String,
    val phoneNumber: String,
    val age: Int,
    val selfIntroduce: String?,
    val interestCategoryNames: List<String>?
) {
    constructor(member: Member) : this(
        id = member.id,
        name = member.name,
        email = member.email.value,
        phoneNumber = member.phoneNumber,
        age = member.age,
        selfIntroduce = member.selfIntroduce,
        interestCategoryNames = member.interestCategories?.map { it.categoryName }
    )

    constructor(member: Member, interestCategoryNames: List<String>) : this(
        id = member.id,
        name = member.name,
        email = member.email.value,
        phoneNumber = member.phoneNumber,
        age = member.age,
        selfIntroduce = member.selfIntroduce,
        interestCategoryNames = interestCategoryNames
    )
}
