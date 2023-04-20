package core.member.usecase.result

import core.member.model.Category
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
    val interestCategoryNames: List<Category>?
) {
    constructor(member: Member) : this(
        id = member.id,
        name = member.name,
        email = member.email.value,
        phoneNumber = member.phoneNumber,
        age = member.age,
        selfIntroduce = member.selfIntroduce,
        interestCategoryNames = member.interestCategories?.map { it.category }
    )

    constructor(member: Member, interestCategoryNames: List<Category>) : this(
        id = member.id,
        name = member.name,
        email = member.email.value,
        phoneNumber = member.phoneNumber,
        age = member.age,
        selfIntroduce = member.selfIntroduce,
        interestCategoryNames = interestCategoryNames
    )
}
