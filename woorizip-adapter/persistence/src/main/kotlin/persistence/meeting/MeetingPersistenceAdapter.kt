package persistence.meeting

import core.meeting.model.Meeting
import core.meeting.spi.CommandMeetingPort
import core.meeting.spi.QueryMeetingPort
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import persistence.meeting.model.MeetingTable
import persistence.meeting.repository.MeetingRepository

class MeetingPersistenceAdapter(
    private val meetingRepository: MeetingRepository
) : QueryMeetingPort, CommandMeetingPort {
    override suspend fun saveMeeting(meeting: Meeting): Meeting {
        return meetingRepository.insertMeeting(meeting)
    }

    override suspend fun getMeetingById(id: Int): Meeting? {
        return meetingRepository.findMeetingBy { MeetingTable.id eq id }
    }
}
