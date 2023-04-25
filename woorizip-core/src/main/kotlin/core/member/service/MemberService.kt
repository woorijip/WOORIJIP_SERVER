package core.member.service

class MemberService(
    memberUseCaseService: MemberUseCaseService,
    queryMemberService: QueryMemberService,
    commandMemberService: CommandMemberService,
    checkMemberService: CheckMemberService
) : MemberUseCaseService by memberUseCaseService,
    QueryMemberService by queryMemberService,
    CommandMemberService by commandMemberService,
    CheckMemberService by checkMemberService
