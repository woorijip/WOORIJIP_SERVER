package core.meeting.service

import core.meeting.model.Meeting
import core.meeting.spi.CommandMeetingPort

interface MeetingUseCaseService {
    suspend fun create(meeting: Meeting): Meeting
}

class MeetingUseCaseServiceImpl(
    private val commandMeetingPort: CommandMeetingPort
) : MeetingUseCaseService {
    override suspend fun create(meeting: Meeting): Meeting {
        return commandMeetingPort.saveMeeting(meeting)
    }
}
