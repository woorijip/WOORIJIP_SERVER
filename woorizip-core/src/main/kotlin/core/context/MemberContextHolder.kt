package core.context

private val MemberContextThread: ThreadLocal<MemberContextHolder.MemberContext> = ThreadLocal.withInitial {
    MemberContextHolder.MemberContext()
}

object MemberContextHolder {
    class MemberContext(
        var memberId: Int? = null
    )

    fun getMemberId(): Int? {
        return MemberContextThread.get().memberId
    }

    fun setMemberId(memberId: Int) {
        MemberContextThread.set(MemberContext(memberId = memberId))
    }

    fun clear() {
        MemberContextThread.remove()
    }
}
