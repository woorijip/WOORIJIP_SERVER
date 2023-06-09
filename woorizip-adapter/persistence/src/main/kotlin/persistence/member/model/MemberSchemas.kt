package persistence.member.model

import core.meeting.model.Category
import core.member.model.Email
import core.member.model.InterestCategory
import core.member.model.Member
import core.member.model.Password
import org.jetbrains.exposed.sql.ResultRow
import persistence.common.BaseTable

object MemberTable : BaseTable("tbl_member") {
    val name = varchar("name", length = Member.NAME_MAX_LENGTH)
    val email = varchar("email", length = Email.EMAIL_MAX_LENGTH).uniqueIndex("UK_Member_Email")
    val password = char("password", length = Password.ENCODED_PASSWORD_LENGTH)
    val phoneNumber = varchar("phone", length = Member.PHONE_NUMBER_MAX_LENGTH).uniqueIndex("UK_Member_Phone")
    val age = integer("age")
    val selfIntroduce = varchar("self_introduce", length = Member.SELF_INTRODUCE_MAX_LENGTH).nullable()
}

internal fun MemberTable.toDomain(row: ResultRow?): Member? {
    return row?.let {
        Member(
            id = row[this.id].value,
            name = row[this.name],
            email = Email(row[this.email]),
            password = Password(row[this.password]),
            phoneNumber = row[this.phoneNumber],
            age = row[this.age],
            selfIntroduce = row[this.selfIntroduce],
            interestCategories = emptyList()
        )
    }
}

object InterestCategoryTable : BaseTable("tbl_interest_category") {
    val categoryName = enumerationByName<Category>("category_name", length = 20)
    val memberId = reference("member_id", MemberTable)
}

internal fun InterestCategoryTable.toDomain(row: ResultRow?): InterestCategory? {
    return row?.let {
        InterestCategory(
            id = row[this.id].value,
            category = row[this.categoryName],
            memberId = row[this.memberId].value
        )
    }
}
