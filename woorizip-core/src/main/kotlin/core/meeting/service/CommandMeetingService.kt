package core.meeting.service

import core.meeting.exception.MeetingNotFoundException
import core.meeting.model.Meeting
import core.meeting.spi.CommandMeetingPort
import core.meeting.spi.QueryMeetingPort

interface CommandMeetingService {
    suspend fun createMeeting(meeting: Meeting): Meeting
    suspend fun removeMeeting(meetingId: Long)
}

class CommandMeetingServiceImpl(
    private val commandMeetingPort: CommandMeetingPort,
    private val queryMeetingPort: QueryMeetingPort
) : CommandMeetingService {
    override suspend fun createMeeting(meeting: Meeting): Meeting {
        return commandMeetingPort.createMeeting(meeting)
    }

    override suspend fun removeMeeting(meetingId: Long) {
        val meeting = queryMeetingPort.getMeetingById(meetingId) ?: throw MeetingNotFoundException(meetingId)

        return commandMeetingPort.removeMeeting(meeting)
    }
}
