package core.member.exception

import common.exception.BaseException
import common.exception.ErrorCode

enum class MemberErrorCode(
    override val sequence: Int,
    override val message: String
) : ErrorCode {

    OUT_OF_LENGTH_LIMIT(1, "Out of length limit"),
    MEMBER_NOT_FOUND(2, "Member is not found"),
    ALREADY_EXISTS(3, "Member Already Exists"),
    PASSWORD_MISMATCH(4, "Password is not matched")

    ;

    val code = getCode(prefix = "MEMBER")

    companion object {
        fun from(code: String) = values().firstOrNull { it.code == code }
            ?: throw BaseException.UnhandledException(message = "해당 에러 코드는 존재하지 않습니다.")
    }
}
