package persistence.member.repository

import core.meeting.model.Category
import core.member.model.InterestCategory
import core.member.model.Member
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import persistence.member.model.InterestCategoryTable
import persistence.member.model.MemberTable
import persistence.member.model.toDomain

class MemberRepositoryImpl : MemberRepository {
    override suspend fun findBy(where: () -> Op<Boolean>): Member? {
        return MemberTable
            .select(where())
            .singleOrNull()
            ?.let { MemberTable.toDomain(it) }
    }

    override suspend fun existsBy(where: () -> Op<Boolean>): Boolean {
        return MemberTable
            .select(where())
            .empty().not()
    }

    override suspend fun insertMember(member: Member): Member {
        return MemberTable
            .insert {
                it[id] = member.id
                it[name] = member.name
                it[email] = member.email.value
                it[password] = member.password.value
                it[phoneNumber] = member.phoneNumber
                it[age] = member.age
                it[selfIntroduce] = member.selfIntroduce
            }
            .resultedValues?.single()
            .let { MemberTable.toDomain(it)!! }
    }

    override suspend fun insertAllInterestCategories(
        memberId: Long,
        categories: List<Category>
    ): List<InterestCategory> {
        return InterestCategoryTable
            .batchInsert(categories) { category ->
                this[InterestCategoryTable.categoryName] = category
                this[InterestCategoryTable.memberId] = memberId
            }
            .map { InterestCategoryTable.toDomain(it)!! }
    }
}
