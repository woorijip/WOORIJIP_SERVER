package core.meeting.service

import core.meeting.spi.QueryMeetingPort

interface QueryMeetingService

class QueryMeetingServiceImpl(private val queryMeetingPort: QueryMeetingPort) : QueryMeetingService
