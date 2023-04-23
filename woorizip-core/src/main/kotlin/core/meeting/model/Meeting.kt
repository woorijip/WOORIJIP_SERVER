package core.meeting.model

import common.exception.BaseException
import core.annotation.AggregateRoot
import core.annotation.SubDomain
import core.meeting.exception.OutOfLengthLimitException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@AggregateRoot
data class Meeting(
    val id: Int = 0,
    val name: String,
    val introduction: String,
    val thumbnail: String,
    val space: Space,
    val description: String,
    val meetingSchedules: List<MeetingSchedule>,
    val categories: List<MeetingCategory>,
    val createMemberId: Int,
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
        val images: List<String>
    ) {

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
    val meetingId: Int,
    val date: LocalDate,
    val time: LocalTime,
    val maxMember: Int,
)

@SubDomain
data class MeetingCategory(
    val category: Category,
    val meetingId: Int
) {
    init {
        if (category.value.length > CATEGORY_NAME_MAX_LENGTH) {
            throw OutOfLengthLimitException(
                lengths = category.value.length,
                message = "모임 카테고리는 $CATEGORY_NAME_MAX_LENGTH 글자 이하로 이루어져야 합니다."
            )
        }
    }

    companion object {
        const val CATEGORY_NAME_MAX_LENGTH = 20
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
