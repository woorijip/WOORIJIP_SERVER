package core.member.exception

import common.exception.BaseErrorCode
import common.exception.ErrorCode

enum class MemberErrorCode(
    val message: String
) : ErrorCode {

    MEMBER_NOT_FOUND("Member is not found")

    ;

    val code = getCode(prefix = "MEMBER", ordinal = this.ordinal)
    override fun getCode(prefix: String, ordinal: Int): String = code

    companion object {
        fun from(code: String) = values().firstOrNull { it.code == code } ?: BaseErrorCode.UNHANDLED_EXCEPTION
    }
}