package persistence.member.repository

import core.member.model.InterestCategory
import core.member.model.Member
import org.jetbrains.exposed.sql.Op

interface MemberRepository {
    suspend fun findBy(where: () -> Op<Boolean>): Member?
    suspend fun existsBy(where: () -> Op<Boolean>): Boolean
    suspend fun saveMember(member: Member): Member
    suspend fun saveAllInterestCategories(categories: List<InterestCategory>): List<InterestCategory>
}