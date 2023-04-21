package core.meeting.usecase.result

import core.meeting.model.Category
import core.meeting.model.Meeting
import core.meeting.model.MeetingSchedule
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class MeetingOutput(
    val id: Int,
    val name: String,
    val introduction: String,
    val thumbnail: String,
    val location: String,
    val type: String,
    val images: List<String>,
    val description: String,
    val schedules: List<ScheduleOutput>,
    val categories: List<Category>,
    val createMemberId: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    data class ScheduleOutput(
        val meetingId: Int,
        val date: LocalDate,
        val time: LocalTime,
        val maxMember: Int
    ) {
        companion object {
            fun of(meetingSchedule: MeetingSchedule) = ScheduleOutput(
                meetingId = meetingSchedule.meetingId,
                date = meetingSchedule.date,
                time = meetingSchedule.time,
                maxMember = meetingSchedule.maxMember
            )
        }
    }

    companion object {
        fun of(meeting: Meeting) = MeetingOutput(
            id = meeting.id,
            name = meeting.name,
            introduction = meeting.introduction,
            thumbnail = meeting.thumbnail,
            location = meeting.space.location,
            type = meeting.space.type.value,
            images = meeting.space.images,
            description = meeting.description,
            schedules = meeting.meetingSchedules.map { ScheduleOutput.of(it) },
            categories = meeting.categories.map { it.category },
            createMemberId = meeting.createMemberId,
            createdAt = meeting.createdAt,
            updatedAt = meeting.updatedAt
        )
    }
}
