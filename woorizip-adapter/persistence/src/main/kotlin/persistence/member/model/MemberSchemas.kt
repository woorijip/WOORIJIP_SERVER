package persistence.member.model

import core.member.model.Email
import core.member.model.InterestCategory
import core.member.model.Member
import core.member.model.Password
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

object MemberTable : Table("tbl_member") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", length = Member.NAME_MAX_LENGTH)
    val email = varchar("email", length = Email.EMAIL_MAX_LENGTH).uniqueIndex("UK_Member_Email")
    val password = char("password", length = 60)
    val phoneNumber = varchar("phone", length = Member.PHONE_NUMBER_MAX_LENGTH).uniqueIndex("UK_Member_Phone")
    val age = integer("age")
    val selfIntroduce = varchar("self_introduce", length = Member.SELF_INTRODUCE_MAX_LENGTH).nullable()

    override val primaryKey = PrimaryKey(id)
}

fun MemberTable.toDomain(row: ResultRow) = Member(
    id = row[this.id],
    name = row[this.name],
    email = Email(row[this.email]),
    password = Password(row[this.password]),
    phoneNumber = row[this.phoneNumber],
    age = row[this.age],
    selfIntroduce = row[this.selfIntroduce]
)

object InterestCategoryTable : Table("tbl_interest_category") {
    val categoryName = varchar("category_name", length = 15)
    val memberId = reference("member_id", MemberTable.id)

    override val primaryKey = PrimaryKey(categoryName, memberId)
}

fun InterestCategoryTable.toDomain(row: ResultRow) = InterestCategory(
    categoryName = row[this.categoryName],
    memberId = row[this.memberId]
)
