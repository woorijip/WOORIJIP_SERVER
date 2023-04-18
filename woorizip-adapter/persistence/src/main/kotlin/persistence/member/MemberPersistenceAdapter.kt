package persistence.member

import core.member.model.Member
import core.member.spi.CommandMemberPort
import core.member.spi.QueryMemberPort
import persistence.member.repository.MemberRepository

class MemberPersistenceAdapter(
    private val memberRepository: MemberRepository
) : QueryMemberPort, CommandMemberPort {
    override suspend fun save(member: Member): Member {
        return memberRepository.save(member)
    }
}