package core.member.exception

import common.exception.BaseException

class MemberNotFoundException(
    private val id: Int,
    override val code: String = MemberErrorCode.MEMBER_NOT_FOUND.code,
    override val message: String = MemberErrorCode.MEMBER_NOT_FOUND.message,
) : BaseException.NotFoundException(code, message) {
    override fun messageArguments(): Collection<String> = setOf(id.toString())
}

class OutOfLengthLimitException(
    private val lengths: Int,
    override val code: String = MemberErrorCode.OUT_OF_LENGTH_LIMIT.code,
    override val message: String = MemberErrorCode.OUT_OF_LENGTH_LIMIT.message,
) : BaseException.BadRequestException(code, message) {
    override fun messageArguments(): Collection<String> = setOf(lengths.toString())
}
