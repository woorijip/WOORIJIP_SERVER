package web.context

object MemberContextHolder {

    private val contextThread: ThreadLocal<MemberContext> = ThreadLocal.withInitial { MemberContext() }

    class MemberContext(
        var memberId: Int? = null
    )

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
