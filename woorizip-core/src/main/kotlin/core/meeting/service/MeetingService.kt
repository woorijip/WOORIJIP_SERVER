package core.meeting.service

class MeetingService(
    queryMeetingService: QueryMeetingService,
    commandMeetingService: CommandMeetingService,
    commandMeetingExternalService: CommandMeetingExternalService,
    checkMeetingService: CheckMeetingService
) : QueryMeetingService by queryMeetingService,
    CommandMeetingService by commandMeetingService,
    CommandMeetingExternalService by commandMeetingExternalService,
    CheckMeetingService by checkMeetingService
