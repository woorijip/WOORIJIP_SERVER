package core.member.model

import core.annotation.AggregateRoot
import core.annotation.SubDomain

@AggregateRoot
data class Member(
    val id: Int,
    val name: String,
    val email: Email,
    val password: Password,
    val phoneNumber: String,
    val age: Int,
    val selfIntroduce: String?
) {
    init {
        if (name.length > NAME_MAX_LENGTH) {
            throw IllegalArgumentException()
        }
    }

    companion object {
        const val NAME_MAX_LENGTH = 5
        const val PHONE_NUMBER_MAX_LENGTH = 11
        const val SELF_INTRODUCE_MAX_LENGTH = 200
    }
}

@SubDomain
data class InterestCategory(
    val categoryName: String,
    val memberId: Int
) {
    init {
        if (categoryName.length > CATEGORY_NAME_MAX_LENGTH) {
            throw IllegalArgumentException()
        }
    }

    companion object {
        const val CATEGORY_NAME_MAX_LENGTH = 15
    }
}

@JvmInline
value class Email(
    private val value: String
) {
    init {
        if (value.length > EMAIL_MAX_LENGTH) {
            throw IllegalArgumentException()
        }
    }

    companion object {
        const val EMAIL_MAX_LENGTH = 255
    }
}

@JvmInline
value class Password(
    private val value: String
) {
    init {
        if (value.length > PASSWORD_MAX_LENGTH || value.length < PASSWORD_MIN_LENGTH) {
            throw IllegalArgumentException()
        }
    }

    companion object {
        const val PASSWORD_MIN_LENGTH = 8
        const val PASSWORD_MAX_LENGTH = 32
    }
}
