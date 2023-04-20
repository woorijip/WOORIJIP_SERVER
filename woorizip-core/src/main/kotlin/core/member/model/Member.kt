package core.member.model

import common.exception.BaseErrorCode
import common.exception.BaseException
import core.annotation.AggregateRoot
import core.annotation.SubDomain
import core.member.exception.OutOfLengthLimitException
import core.member.exception.PasswordMisMatchException
import kotlinx.serialization.Serializable
import org.mindrot.jbcrypt.BCrypt

@Serializable
@AggregateRoot
data class Member(
    val id: Int = 0,
    val name: String,
    val email: Email,
    val password: Password,
    val phoneNumber: String,
    val age: Int,
    val selfIntroduce: String?,
    val interestCategories: List<InterestCategory>? = null
) {
    init {
        if (name.length > NAME_MAX_LENGTH) {
            throw OutOfLengthLimitException(
                lengths = name.length,
                message = "이름은 $NAME_MAX_LENGTH 글자 이하로 이루어져야 합니다."
            )
        } else if (phoneNumber.length > PHONE_NUMBER_MAX_LENGTH) {
            throw OutOfLengthLimitException(
                lengths = phoneNumber.length,
                message = "전화번호는 $PHONE_NUMBER_MAX_LENGTH 글자 이하로 이루어져야 합니다."
            )
        } else if (selfIntroduce != null && selfIntroduce.length > SELF_INTRODUCE_MAX_LENGTH) {
            throw OutOfLengthLimitException(
                lengths = selfIntroduce.length,
                message = "자기소개는 $SELF_INTRODUCE_MAX_LENGTH 글자 이하로 이루어져야 합니다."
            )
        }
    }

    fun checkIsSamePassword(rawPassword: Password) {
        if (!this.password.isSame(rawPassword)) {
            throw PasswordMisMatchException(rawPassword.value)
        }
    }

    companion object {
        const val NAME_MAX_LENGTH = 5
        const val PHONE_NUMBER_MAX_LENGTH = 11
        const val SELF_INTRODUCE_MAX_LENGTH = 200
    }
}

@Serializable
@SubDomain
data class InterestCategory(
    val category: Category,
    val memberId: Int
) {
    init {
        if (category.categoryName.length > CATEGORY_NAME_MAX_LENGTH) {
            throw OutOfLengthLimitException(
                lengths = category.categoryName.length,
                message = "관심 카테고리는 $CATEGORY_NAME_MAX_LENGTH 글자 이하로 이루어져야 합니다."
            )
        }
    }

    companion object {
        const val CATEGORY_NAME_MAX_LENGTH = 20
    }
}

enum class Category(val categoryName: String) {
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
            return values().firstOrNull { it.categoryName == name }
                ?: throw BaseException.UnhandledException(message = "해당 카테고리는 존재하지 않습니다.")
        }
    }
}

@Serializable
@JvmInline
value class Email(
    val value: String
) {
    init {
        if (value.length > EMAIL_MAX_LENGTH) {
            throw OutOfLengthLimitException(
                lengths = value.length,
                message = "이메일은 $EMAIL_MAX_LENGTH 글자 이하로 이루어져야 합니다."
            )
        }
    }

    companion object {
        const val EMAIL_MAX_LENGTH = 255
    }
}

@Serializable
@JvmInline
value class Password(
    val value: String
) {
    init {
        if (
            (value.length > PASSWORD_MAX_LENGTH || value.length < PASSWORD_MIN_LENGTH) &&
            value.length != ENCODED_PASSWORD_LENGTH
        ) {
            throw OutOfLengthLimitException(
                lengths = value.length,
                message = "비밀번호는 $PASSWORD_MIN_LENGTH ~ $PASSWORD_MAX_LENGTH 글자로 이루어져야 합니다."
            )
        }
    }

    fun isSame(rawPassword: Password): Boolean {
        return BCrypt.checkpw(rawPassword.value, this.value)
    }

    fun encode(): Password {
        return Password(
            BCrypt.hashpw(this.value, BCrypt.gensalt())
        )
    }

    companion object {
        private const val PASSWORD_MIN_LENGTH = 8
        private const val PASSWORD_MAX_LENGTH = 32
        const val ENCODED_PASSWORD_LENGTH = 60
    }
}
