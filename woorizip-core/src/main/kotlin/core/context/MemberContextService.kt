package core.context

private val MemberContextHolder: ThreadLocal<MemberContextService.MemberContext> = ThreadLocal.withInitial {
    MemberContextService.MemberContext()
}

object MemberContextService {
    class MemberContext(
        var memberId: Int? = null
    )

    fun getMemberId(): Int? {
        return MemberContextHolder.get().memberId
    }

    fun setMemberId(memberId: Int) {
        MemberContextHolder.set(MemberContext(memberId = memberId))
    }

    fun clear() {
        MemberContextHolder.remove()
    }
}
