package core.member.exception

import common.exception.BaseErrorCode
import common.exception.ErrorCode

enum class MemberErrorCode(
    val message: String
) : ErrorCode {

    OUT_OF_LENGTH_LIMIT("Out of length limit"),

    MEMBER_NOT_FOUND("Member is not found")

    ;

    val code = getCode(prefix = "MEMBER", ordinal = this.ordinal)

    companion object {
        fun from(code: String) = values().firstOrNull { it.code == code } ?: BaseErrorCode.UNHANDLED_EXCEPTION
    }
}
