package core.meeting.usecase.result

import core.meeting.model.Category
import core.meeting.model.Meeting
import core.meeting.model.MeetingSchedule
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class MeetingOutput(
    val id: Long,
    val name: String,
    val introduction: String,
    val thumbnail: String,
    val location: String,
    val type: String,
    val images: List<String>,
    val description: String,
    val schedules: List<ScheduleOutput>,
    val categories: List<Category>,
    val meetingCount: Int,
    val createMemberId: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val reviews: List<ReviewOutput> = emptyList()
) {
    data class ScheduleOutput(
        val date: LocalDate,
        val time: LocalTime,
        val maxMember: Int
    ) {
        companion object {
            fun of(meetingSchedule: MeetingSchedule) = ScheduleOutput(
                date = meetingSchedule.date,
                time = meetingSchedule.time,
                maxMember = meetingSchedule.maxMember
            )
        }
    }

    data class ReviewOutput(
        val name: String,
        val date: LocalDate,
        val content: String
    ) {
        companion object {
            fun of() = ReviewOutput( // TODO 매개변수 추가
                name = "",
                date = LocalDate.now(),
                content = ""
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
            images = meeting.space.images.map { it.image },
            description = meeting.description,
            schedules = meeting.meetingSchedules.map { ScheduleOutput.of(it) },
            categories = meeting.categories.map { it.category },
            meetingCount = meeting.meetingCount,
            createMemberId = meeting.createMemberId,
            createdAt = meeting.createdAt,
            updatedAt = meeting.updatedAt
        )
    }

    fun withReviews() = this.copy(reviews = reviews) // TODO 매개변수 추가
}
