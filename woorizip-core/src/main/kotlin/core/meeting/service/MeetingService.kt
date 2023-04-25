package core.meeting.service

class MeetingService(
    meetingUseCaseService: MeetingUseCaseService,
    queryMeetingService: QueryMeetingService,
    commandMeetingService: CommandMeetingService
) : MeetingUseCaseService by meetingUseCaseService,
    QueryMeetingService by queryMeetingService,
    CommandMeetingService by commandMeetingService
