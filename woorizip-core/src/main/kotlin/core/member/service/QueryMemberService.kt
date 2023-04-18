package core.member.service

import core.member.spi.QueryMemberPort

interface QueryMemberService {

}

class QueryMemberServiceImpl(private val queryMemberPort: QueryMemberPort) : QueryMemberService {

}
