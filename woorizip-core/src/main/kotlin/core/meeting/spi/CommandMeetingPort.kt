package core.meeting.spi

import core.meeting.model.Meeting

interface CommandMeetingPort {
    suspend fun createMeeting(meeting: Meeting): Meeting
    suspend fun removeMeeting(meeting: Meeting)
}
