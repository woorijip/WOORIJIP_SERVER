package core.meeting.model

import common.exception.BaseException
import core.annotation.AggregateRoot
import core.annotation.SubDomain
import core.meeting.exception.OutOfLengthLimitException
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@AggregateRoot
data class Meeting(
    val id: Long = 0,
    val name: String,
    val introduction: String,
    val thumbnail: String,
    val space: Space,
    val description: String,
    val meetingSchedules: List<MeetingSchedule>,
    val categories: List<MeetingCategory>,
    val meetingCount: Int = 0,
    val createMemberId: Long,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = createdAt
) {
    init {
        if (name.length > NAME_MAX_LENGTH) {
            throw OutOfLengthLimitException(
                lengths = name.length,
                message = "모임 이름은 $NAME_MAX_LENGTH 글자 이하로 이루어져야 합니다."
            )
        } else if (introduction.length > INTRODUCTION_MAX_LENGTH) {
            throw OutOfLengthLimitException(
                lengths = introduction.length,
                message = "모임 한 줄 소개는 $INTRODUCTION_MAX_LENGTH 글자 이하로 이루어져야 합니다."
            )
        }
    }

    data class Space(
        val location: String,
        val type: SpaceType,
        val images: List<MeetingImage>
    ) {

        data class MeetingImage(
            val id: Long = 0,
            val image: String
        )

        enum class SpaceType(val value: String) {
            HOUSE("집"),
            STORE("가게"),
            COMPANY("회사"),
            WORKSHOP("공방"),
            WORKSPACE("작업실"),
            SHARED_SPACE("공유 공간")
            ;
        }
    }

    companion object {
        const val NAME_MAX_LENGTH = 30
        const val INTRODUCTION_MAX_LENGTH = 250
    }
}

@SubDomain
data class MeetingSchedule(
    val id: Long = 0,
    val date: LocalDate,
    val time: LocalTime,
    val maxMember: Int
) {
    val weekType: WeekType
        get() = when (date.dayOfWeek) {
            DayOfWeek.SATURDAY, DayOfWeek.SUNDAY -> WeekType.WEEKEND
            else -> WeekType.WEEKDAY
        }
}

@SubDomain
data class MeetingCategory(
    val id: Long = 0,
    val category: Category
)

enum class WeekType {
    ALL, WEEKDAY, WEEKEND
}

fun List<WeekType>.containsType(reqType: WeekType): Boolean {
    return if (reqType == WeekType.ALL) {
        true
    } else {
        this.contains(reqType)
    }
}

enum class Category(val value: String) {
    HUMAN_RELATIONSHIP("인간관계(친목)"),
    ALCOHOL("술"),
    SELF_IMPROVEMENT("자기계발/공부"),
    ART("예술"),
    SPORTS("스포츠/운동"),
    FOOD("음식"),
    LIFE("라이프"),
    CRAFT("공예/만들기"),
    BOOK("책/글쓰기/독서"),
    TEE("차/음료"),
    CAREER("커리어/직장"),
    REINVESTMENT("재테크"),
    PET("반려동물"),
    GAME("게임/액티비티"),
    TRAVEL("여행"),
    PSYCHOLOGY("심리/상담"),
    INTERIOR("인테리어/가구"),
    HEALTH("건강"),
    ENVIRONMENT("환경"),
    BEAUTY("미용"),
    TREND("트렌드"),
    LOVE("연애/이성관계"),
    PLANT("식물/자연")
    ;

    companion object {
        fun from(name: String): Category {
            return values().firstOrNull { it.value == name }
                ?: throw BaseException.UnhandledException(message = "해당 카테고리는 존재하지 않습니다.")
        }
    }
}
