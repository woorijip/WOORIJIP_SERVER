package core.member.exception

import common.exception.BaseException

class MemberNotFoundException(
    private val id: Int,
    override val code: String = MemberErrorCode.MEMBER_NOT_FOUND.code,
    override val message: String = MemberErrorCode.MEMBER_NOT_FOUND.message,
) : BaseException.NotFoundException(code, message) {
    override fun messageArguments(): Collection<String> = setOf(id.toString())
}