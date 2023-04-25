package core.meeting.spi

import core.meeting.model.Meeting

interface QueryMeetingPort {
    suspend fun getMeetingById(id: Int): Meeting?
}
