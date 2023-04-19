package core.member.model

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
    val categoryName: String,
    val memberId: Int
) {
    init {
        if (categoryName.length > CATEGORY_NAME_MAX_LENGTH) {
            throw OutOfLengthLimitException(
                lengths = categoryName.length,
                message = "관심 카테고리는 $CATEGORY_NAME_MAX_LENGTH 글자 이하로 이루어져야 합니다."
            )
        }
    }

    companion object {
        const val CATEGORY_NAME_MAX_LENGTH = 15
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
