package core.meeting

import core.meeting.model.Category
import core.meeting.model.Meeting
import core.meeting.model.MeetingSchedule
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

internal fun createMeeting(
    id: Int = 1,
    name: String = "룰루랄라 모임",
    introduction: String = "안녕하세요 룰루랄라 입니다",
    thumbnail: String = "https://image.beomjin.kim/룰루랄라모임.png",
    space: Meeting.Space = Meeting.Space(
        location = "서울시 강남구",
        type = Meeting.Space.SpaceType.SHARED_SPACE,
        images = listOf("https://image.beomjin.kim/룰루랄라모임.png", "https://image.beomjin.kim/룰루랄라모임2.png")
    ),
    description: String = "룰루랄라 설명~",
    meetingSchedules: List<MeetingSchedule> = listOf(
        MeetingSchedule(
            date = LocalDate.of(2023, 4, 26),
            time = LocalTime.of(10, 0),
            maxMember = 5
        ),
        MeetingSchedule(
            date = LocalDate.of(2023, 4, 27),
            time = LocalTime.of(12, 30),
            maxMember = 4
        )
    ),
    categories: List<Category> = listOf(Category.FOOD, Category.LOVE, Category.TREND),
    createMemberId: Int = 1,
    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime = LocalDateTime.now()
): Meeting = Meeting(
    id = id,
    name = name,
    introduction = introduction,
    thumbnail = thumbnail,
    space = space,
    description = description,
    meetingSchedules = meetingSchedules,
    categories = categories,
    createMemberId = createMemberId,
    createdAt = createdAt,
    updatedAt = updatedAt
)
