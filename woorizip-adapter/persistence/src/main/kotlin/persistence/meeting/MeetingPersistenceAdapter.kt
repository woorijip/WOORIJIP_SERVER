package persistence.meeting

import core.meeting.model.Meeting
import core.meeting.spi.CommandMeetingPort
import core.meeting.spi.QueryMeetingPort
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
}
