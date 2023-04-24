package core.meeting.spi

import core.meeting.model.Meeting

interface CommandMeetingPort {
    suspend fun saveMeeting(meeting: Meeting): Meeting
}
