package core.member.service

class MemberService(
    queryMemberService: QueryMemberService,
    commandMemberService: CommandMemberService,
    commandMemberExternalService: CommandMemberExternalService,
    checkMemberService: CheckMemberService
) : QueryMemberService by queryMemberService,
    CommandMemberService by commandMemberService,
    CommandMemberExternalService by commandMemberExternalService,
    CheckMemberService by checkMemberService
