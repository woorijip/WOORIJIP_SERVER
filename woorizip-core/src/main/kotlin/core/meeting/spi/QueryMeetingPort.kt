package core.meeting.spi

import core.meeting.model.Category
import core.meeting.model.Meeting
import core.meeting.model.WeekType

interface QueryMeetingPort {
    suspend fun getMeetingById(id: Long): Meeting?
    suspend fun getMeetings(categories: List<Category>, weekType: WeekType, name: String): List<Meeting>
}
