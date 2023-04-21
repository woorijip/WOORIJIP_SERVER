package core.meeting.exception

import common.exception.BaseException
import common.exception.ErrorCode

enum class MeetingErrorCode(
    override val sequence: Int,
    override val message: String
) : ErrorCode {

    OUT_OF_LENGTH_LIMIT(1, "Out of length limit"),
    MEETING_NOT_FOUND(2, "Meeting is not found"),
    ALREADY_EXISTS(3, "Meeting Already Exists")

    ;

    val code = getCode(prefix = "MEETING")

    companion object {
        fun from(code: String) = values().firstOrNull { it.code == code }
            ?: throw BaseException.UnhandledException(message = "해당 에러 코드는 존재하지 않습니다.")
    }
}
