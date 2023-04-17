package common.exception

enum class BaseErrorCode(
    override val sequence: Int,
    override val message: String
) : ErrorCode {

    UNHANDLED_EXCEPTION(1, "Unhandled exception")

    ;

    val code = getCode(prefix = "ERROR")

    companion object {
        fun from(code: String) = values().firstOrNull { it.code == code } ?: UNHANDLED_EXCEPTION
    }
}
