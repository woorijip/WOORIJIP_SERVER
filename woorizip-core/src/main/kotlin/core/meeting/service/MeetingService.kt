package core.meeting.service

class MeetingService(
    queryMemberService: QueryMeetingService,
    commandMemberService: CommandMeetingService,
    checkMeetingService: CheckMeetingService
) : QueryMeetingService by queryMemberService,
    CommandMeetingService by commandMemberService,
    CheckMeetingService by checkMeetingService
