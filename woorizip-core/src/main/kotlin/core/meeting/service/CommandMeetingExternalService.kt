package core.meeting.service

import core.meeting.model.Meeting
import core.meeting.spi.CommandMeetingPort

interface CommandMeetingExternalService {
    suspend fun saveMeeting(meeting: Meeting): Meeting
}

class CommandMeetingExternalServiceImpl(
    private val commandMeetingPort: CommandMeetingPort
) : CommandMeetingExternalService {
    override suspend fun saveMeeting(meeting: Meeting): Meeting {
        return commandMeetingPort.saveMeeting(meeting)
    }
}
