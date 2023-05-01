package persistence.member

import core.member.model.InterestCategory
import core.member.model.Member
import core.member.spi.CommandMemberPort
import core.member.spi.QueryMemberPort
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import persistence.member.model.MemberTable
import persistence.member.repository.MemberRepository

class MemberPersistenceAdapter(
    private val memberRepository: MemberRepository
) : QueryMemberPort, CommandMemberPort {
    override suspend fun existsMemberByEmail(email: String): Boolean {
        return memberRepository.existsBy { MemberTable.email eq email }
    }

    override suspend fun existsMemberByPhoneNumber(phoneNumber: String): Boolean {
        return memberRepository.existsBy { MemberTable.phoneNumber eq phoneNumber }
    }

    override suspend fun getMemberByEmail(email: String): Member? {
        return memberRepository.findBy { MemberTable.email eq email }
    }

    override suspend fun getMemberById(id: Long): Member? {
        return memberRepository.findBy { MemberTable.id eq id }
    }

    override suspend fun createMember(member: Member): Member {
        return memberRepository.insertMember(member)
    }

    override suspend fun createAllInterestCategories(
        memberId: Long,
        interestCategories: List<InterestCategory>
    ): List<InterestCategory> {
        return memberRepository.insertAllInterestCategories(memberId, interestCategories.map { it.category })
    }
}
