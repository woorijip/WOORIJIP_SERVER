package core.member.exception

import common.exception.BaseException
import common.exception.ErrorCode

class MemberNotFoundException(
    private val id: Int,
    override val message: String = "Member($id) is not found",
) : BaseException(ErrorCode.MEMBER_NOT_FOUND, message) {
    override fun messageArguments(): Collection<String> = setOf(id.toString())
}