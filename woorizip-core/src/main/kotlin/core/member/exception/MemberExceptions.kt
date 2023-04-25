package core.member.exception

import common.exception.BaseException

class MemberNotFoundException(
    private val value: Any? = "",
    override val code: String = MemberErrorCode.MEMBER_NOT_FOUND.code,
    override val message: String = MemberErrorCode.MEMBER_NOT_FOUND.message
) : BaseException.NotFoundException(code, message) {
    override fun messageArguments(): Collection<String> = setOf(value.toString())
}

class OutOfLengthLimitException(
    private val lengths: Int,
    override val code: String = MemberErrorCode.OUT_OF_LENGTH_LIMIT.code,
    override val message: String = MemberErrorCode.OUT_OF_LENGTH_LIMIT.message
) : BaseException.BadRequestException(code, message) {
    override fun messageArguments(): Collection<String> = setOf(lengths.toString())
}

class AlreadyExistsException(
    private val value: String = "",
    override val code: String = MemberErrorCode.ALREADY_EXISTS.code,
    override val message: String = MemberErrorCode.ALREADY_EXISTS.message
) : BaseException.ConflictException(code, message) {
    override fun messageArguments(): Collection<String> = setOf(value)
}

class PasswordMisMatchException(
    private val password: String = "",
    override val code: String = MemberErrorCode.PASSWORD_MISMATCH.code,
    override val message: String = MemberErrorCode.PASSWORD_MISMATCH.message
) : BaseException.UnauthorizedException(code, message) {
    override fun messageArguments(): Collection<String> = setOf(password)
}
