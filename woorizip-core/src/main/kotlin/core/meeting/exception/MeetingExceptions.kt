package core.meeting.exception

import common.exception.BaseException

class MeetingNotFoundException(
    private val value: Any,
    override val code: String = MeetingErrorCode.MEETING_NOT_FOUND.code,
    override val message: String = MeetingErrorCode.MEETING_NOT_FOUND.message
) : BaseException.NotFoundException(code, message) {
    override fun messageArguments(): Collection<String> = setOf(value.toString())
}

class OutOfLengthLimitException(
    private val lengths: Int,
    override val code: String = MeetingErrorCode.OUT_OF_LENGTH_LIMIT.code,
    override val message: String = MeetingErrorCode.OUT_OF_LENGTH_LIMIT.message
) : BaseException.BadRequestException(code, message) {
    override fun messageArguments(): Collection<String> = setOf(lengths.toString())
}

class AlreadyExistsException(
    private val value: String,
    override val code: String = MeetingErrorCode.ALREADY_EXISTS.code,
    override val message: String = MeetingErrorCode.ALREADY_EXISTS.message
) : BaseException.ConflictException(code, message) {
    override fun messageArguments(): Collection<String> = setOf(value)
}
