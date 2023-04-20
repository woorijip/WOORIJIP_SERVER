package persistence.member.repository

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

    override suspend fun saveMember(member: Member): Member {
        return MemberTable
            .insert {
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

    override suspend fun saveAllInterestCategories(categories: List<InterestCategory>): List<InterestCategory> {
        return InterestCategoryTable
            .batchInsert(categories, ignore = false, shouldReturnGeneratedValues = true) { category ->
                this[InterestCategoryTable.categoryName] = category.category.value
                this[InterestCategoryTable.memberId] = category.memberId
            }
            .map { InterestCategoryTable.toDomain(it)!! }
    }
}
