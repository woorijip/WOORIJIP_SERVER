package persistence.meeting.repository

import core.meeting.model.Meeting
import core.meeting.model.MeetingCategory
import core.meeting.model.MeetingSchedule
import org.jetbrains.exposed.sql.Op

interface MeetingRepository {
    suspend fun findMeetingBy(where: () -> Op<Boolean>): Meeting?
    suspend fun existsMeetingBy(where: () -> Op<Boolean>): Boolean
    suspend fun insertMeeting(meeting: Meeting): Meeting
    suspend fun insertMeetingImage(meetingId: Long, imageUrls: List<String>): Meeting
    suspend fun insertMeetingSchedule(meetingId: Long, schedules: List<MeetingSchedule>): Meeting
    suspend fun insertMeetingCategory(meetingId: Long, categories: List<MeetingCategory>): Meeting
}
