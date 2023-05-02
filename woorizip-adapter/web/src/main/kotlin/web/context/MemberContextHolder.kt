package web.context

import core.member.exception.MemberNotFoundException

object MemberContextHolder {

    private val contextThread: ThreadLocal<MemberContext> = ThreadLocal.withInitial { MemberContext() }

    class MemberContext(
        var memberId: Long? = null
    ) {
        fun getMemberId() = memberId ?: throw MemberNotFoundException("Member not found in Context holder")
    }

    fun getContext(): MemberContext {
        return contextThread.get()
    }

    fun setContext(ctx: MemberContext) {
        contextThread.set(ctx)
    }

    fun clear() {
        contextThread.remove()
    }
}
