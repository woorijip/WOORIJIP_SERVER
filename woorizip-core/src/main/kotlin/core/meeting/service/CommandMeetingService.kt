package core.meeting.service

import core.meeting.model.Meeting
import core.meeting.spi.CommandMeetingPort

interface CommandMeetingService {
    suspend fun saveMeeting(meeting: Meeting): Meeting
}

class CommandMeetingServiceImpl(
    private val commandMeetingPort: CommandMeetingPort
) : CommandMeetingService {
    override suspend fun saveMeeting(meeting: Meeting): Meeting {
        return commandMeetingPort.saveMeeting(meeting)
    }
}
