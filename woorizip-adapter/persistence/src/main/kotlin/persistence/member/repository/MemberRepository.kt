package persistence.member.repository

import core.member.model.Member
import org.jetbrains.exposed.sql.Op

interface MemberRepository {
    suspend fun findBy(where: () -> Op<Boolean>): Member?
    suspend fun existsBy(where: () -> Op<Boolean>): Boolean
    suspend fun save(member: Member): Member
}
