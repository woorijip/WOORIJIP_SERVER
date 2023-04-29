package core.meeting.service

import core.meeting.exception.MeetingNotFoundException
import core.meeting.model.Meeting
import core.meeting.spi.QueryMeetingPort

interface QueryMeetingService {
    suspend fun getMeetingById(meetingId: Long): Meeting
}

class QueryMeetingServiceImpl(private val queryMeetingPort: QueryMeetingPort) : QueryMeetingService {
    override suspend fun getMeetingById(meetingId: Long): Meeting {
        return queryMeetingPort.getMeetingById(meetingId) ?: throw MeetingNotFoundException(meetingId)
    }
}
