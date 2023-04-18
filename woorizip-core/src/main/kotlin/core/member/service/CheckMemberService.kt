package core.member.service

import core.member.spi.QueryMemberPort

interface CheckMemberService {
}

class CheckMemberServiceImpl(
    private val queryMemberPort: QueryMemberPort
) : CheckMemberService {
}
