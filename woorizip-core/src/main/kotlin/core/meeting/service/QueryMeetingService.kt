package core.meeting.service

import core.meeting.exception.MeetingNotFoundException
import core.meeting.model.Category
import core.meeting.model.Meeting
import core.meeting.model.WeekType
import core.meeting.spi.QueryMeetingPort

interface QueryMeetingService {
    suspend fun getMeetingById(meetingId: Long): Meeting
    suspend fun getMeetings(categories: List<Category>, weekType: WeekType, name: String): List<Meeting>
}

class QueryMeetingServiceImpl(private val queryMeetingPort: QueryMeetingPort) : QueryMeetingService {
    override suspend fun getMeetingById(meetingId: Long): Meeting {
        return queryMeetingPort.getMeetingById(meetingId) ?: throw MeetingNotFoundException(meetingId)
    }

    override suspend fun getMeetings(categories: List<Category>, weekType: WeekType, name: String): List<Meeting> {
        return queryMeetingPort.getMeetings(categories, weekType, name)
    }
}
