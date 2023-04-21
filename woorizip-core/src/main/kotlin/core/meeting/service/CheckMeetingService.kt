package core.meeting.service

import core.meeting.spi.QueryMeetingPort

interface CheckMeetingService

class CheckMeetingServiceImpl(private val queryMeetingPort: QueryMeetingPort) : CheckMeetingService
