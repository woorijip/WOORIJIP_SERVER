package common.exception

sealed class BaseException(
    open val code: String = "",
    override val message: String = "",
) : Throwable() {
    open fun messageArguments(): Collection<String>? = null

    open class BadRequestException(
        override val code: String,
        override val message: String
    ) : BaseException(code, message)

    open class UnauthorizedException(
        override val code: String,
        override val message: String,
    ) : BaseException(code, message)

    open class ForbiddenException(
        override val code: String,
        override val message: String,
    ) : BaseException(code, message)

    open class NotFoundException(
        override val code: String,
        override val message: String,
    ) : BaseException(code, message)

    open class ConflictException(
        override val code: String,
        override val message: String,
    ) : BaseException(code, message)
}
