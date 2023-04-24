package core.member.service

import core.context.MemberContextHolder
import core.member.exception.MemberNotFoundException
import core.member.model.Email
import core.member.model.Member
import core.member.spi.QueryMemberPort

interface QueryMemberService {
    suspend fun getCurrentMember(): Member
    suspend fun getMemberByEmail(email: Email): Member
}

class QueryMemberServiceImpl(private val queryMemberPort: QueryMemberPort) : QueryMemberService {
    override suspend fun getCurrentMember(): Member {
        val memberId = MemberContextHolder.getMemberId() ?: throw MemberNotFoundException(
            message = "Member not found in Context holder"
        )

        return queryMemberPort.getMemberById(memberId) ?: throw MemberNotFoundException(value = memberId)
    }

    override suspend fun getMemberByEmail(email: Email): Member {
        return queryMemberPort.getMemberByEmail(email.value)
            ?: throw MemberNotFoundException(email.value)
    }
}
