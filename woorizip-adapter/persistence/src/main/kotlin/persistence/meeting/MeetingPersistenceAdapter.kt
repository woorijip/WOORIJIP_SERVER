package persistence.meeting

import core.meeting.model.Category
import core.meeting.model.Meeting
import core.meeting.model.WeekType
import core.meeting.model.containsType
import core.meeting.spi.CommandMeetingPort
import core.meeting.spi.QueryMeetingPort
import org.jetbrains.exposed.sql.SqlExpressionBuilder.like
import persistence.meeting.model.MeetingTable
import persistence.meeting.repository.MeetingRepository

class MeetingPersistenceAdapter(
    private val meetingRepository: MeetingRepository
) : QueryMeetingPort, CommandMeetingPort {
    override suspend fun createMeeting(meeting: Meeting): Meeting {
        return meetingRepository.insertMeeting(meeting)
    }

    override suspend fun getMeetingById(id: Long): Meeting? {
        return meetingRepository.findMeetingById(id)
    }

    override suspend fun getMeetings(categories: List<Category>, weekType: WeekType, name: String): List<Meeting> {
        return meetingRepository.findAllMeetings(categories) { MeetingTable.name like "%${name}%" }
            .filter { meeting -> meeting.meetingSchedules.map { it.weekType }.containsType(weekType) }
    }
}
