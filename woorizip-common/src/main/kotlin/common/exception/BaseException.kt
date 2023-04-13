package common.exception

open class BaseException(
    open val errorCode: ErrorCode,
    override val message: String = "",
    override val cause: Throwable? = null
) : RuntimeException(message, cause) {
    open fun messageArguments(): Collection<String>? = null
    open fun details(): Any? = null
}
