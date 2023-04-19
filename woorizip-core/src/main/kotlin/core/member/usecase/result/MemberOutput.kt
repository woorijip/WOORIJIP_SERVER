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
    val interestCategories: List<String>?
) {
    constructor(member: Member) : this(
        id = member.id,
        name = member.name,
        email = member.email.value,
        phoneNumber = member.phoneNumber,
        age = member.age,
        selfIntroduce = member.selfIntroduce,
        interestCategories = member.interestCategories?.map { it.categoryName }
    )

    constructor(member: Member, interestCategories: List<String>) : this(
        id = member.id,
        name = member.name,
        email = member.email.value,
        phoneNumber = member.phoneNumber,
        age = member.age,
        selfIntroduce = member.selfIntroduce,
        interestCategories = interestCategories
    )
}