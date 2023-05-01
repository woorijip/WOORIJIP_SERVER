package core.meeting.service

import core.meeting.model.Meeting
import core.meeting.spi.CommandMeetingPort

interface CommandMeetingService {
    suspend fun createMeeting(meeting: Meeting): Meeting
}

class CommandMeetingServiceImpl(
    private val commandMeetingPort: CommandMeetingPort
) : CommandMeetingService {
    override suspend fun createMeeting(meeting: Meeting): Meeting {
        return commandMeetingPort.createMeeting(meeting)
    }
}
