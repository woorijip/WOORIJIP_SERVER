package core.meeting.spi

import core.meeting.model.Meeting

interface QueryMeetingPort {
    suspend fun getMeetingById(id: Long): Meeting?
}
