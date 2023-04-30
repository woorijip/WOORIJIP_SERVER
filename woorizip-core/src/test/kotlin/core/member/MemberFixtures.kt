package core.member

import core.meeting.model.Category
import core.member.model.Email
import core.member.model.InterestCategory
import core.member.model.Member
import core.member.model.Password

internal fun createMember(
    id: Long = 1,
    name: String = "김범진",
    email: Email = Email("test@test.com"),
    password: Password = Password("test1234"),
    phoneNumber: String = "01012345678",
    age: Int = 19,
    selfIntroduce: String? = null,
    interestCategories: List<InterestCategory>? = null
): Member = Member(
    id = id,
    name = name,
    email = email,
    password = password,
    phoneNumber = phoneNumber,
    age = age,
    selfIntroduce = selfIntroduce,
    interestCategories = interestCategories
)

internal fun createInterestCategory(
    category: Category = Category.FOOD,
    memberId: Long = 1
): InterestCategory = InterestCategory(
    category = category,
    memberId = memberId
)
