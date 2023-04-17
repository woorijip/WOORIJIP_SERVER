package core.member.exception

import common.exception.BaseErrorCode
import common.exception.ErrorCode

enum class MemberErrorCode(
    override val sequence: Int,
    override val message: String
) : ErrorCode {

    OUT_OF_LENGTH_LIMIT(1, "Out of length limit"),
    MEMBER_NOT_FOUND(2, "Member is not found")

    ;

    val code = getCode(prefix = "MEMBER")

    companion object {
        fun from(code: String) = values().firstOrNull { it.code == code } ?: BaseErrorCode.UNHANDLED_EXCEPTION
    }
}
