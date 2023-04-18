package persistence.member.repository

import core.member.model.Member
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import persistence.config.exists
import persistence.member.model.toDomain
import persistence.member.model.MemberTable

class MemberRepositoryImpl : MemberRepository {
    override suspend fun findBy(where: () -> Op<Boolean>): Member? {
        return MemberTable
            .select(where())
            .singleOrNull()
            ?.let { MemberTable.toDomain(it) }
    }

    override suspend fun existsBy(where: () -> Op<Boolean>): Boolean {
        return MemberTable
            .exists(where())
    }

    override suspend fun save(member: Member): Member {
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
            .let { MemberTable.toDomain(it!!) }
    }
}
