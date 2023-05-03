package persistence.meeting.repository

import core.meeting.model.Category
import core.meeting.model.Meeting
import org.jetbrains.exposed.sql.Op

interface MeetingRepository {
    suspend fun findAllMeetings(
        categories: List<Category>,
        where: (() -> Op<Boolean>) = { Op.TRUE }
    ): List<Meeting>

    suspend fun findMeetingById(meetingId: Long): Meeting?
    suspend fun findMeetingBy(where: () -> Op<Boolean>): Meeting?
    suspend fun existsMeetingBy(where: () -> Op<Boolean>): Boolean
    suspend fun insertMeeting(meeting: Meeting): Meeting
    suspend fun deleteMeeting(meeting: Meeting)
}
