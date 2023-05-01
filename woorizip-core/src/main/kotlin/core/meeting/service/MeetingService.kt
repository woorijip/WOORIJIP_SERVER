package core.meeting.service

class MeetingService(
    queryMeetingService: QueryMeetingService,
    commandMeetingService: CommandMeetingService
) : QueryMeetingService by queryMeetingService,
    CommandMeetingService by commandMeetingService
