package core.member.service

import core.member.exception.MemberNotFoundException
import core.member.model.Email
import core.member.model.Member
import core.member.spi.QueryMemberPort

interface QueryMemberService {
    suspend fun getMemberById(memberId: Long): Member
    suspend fun getMemberByEmail(email: Email): Member
}

class QueryMemberServiceImpl(private val queryMemberPort: QueryMemberPort) : QueryMemberService {
    override suspend fun getMemberById(memberId: Long): Member {
        return queryMemberPort.getMemberById(memberId) ?: throw MemberNotFoundException(memberId)
    }

    override suspend fun getMemberByEmail(email: Email): Member {
        return queryMemberPort.getMemberByEmail(email.value) ?: throw MemberNotFoundException(email.value)
    }
}
