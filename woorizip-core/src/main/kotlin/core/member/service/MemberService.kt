package core.member.service

class MemberService(
    queryMemberService: QueryMemberService,
    commandMemberService: CommandMemberService,
    checkMemberService: CheckMemberService
) : QueryMemberService by queryMemberService,
    CommandMemberService by commandMemberService,
    CheckMemberService by checkMemberService
