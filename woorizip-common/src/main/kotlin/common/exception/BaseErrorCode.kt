package common.exception

enum class BaseErrorCode(
    val message: String
) : ErrorCode {

    UNHANDLED_EXCEPTION("Unhandled exception")

    ;

    val code = getCode(prefix = "ERROR", ordinal = this.ordinal)
    override fun getCode(prefix: String, ordinal: Int): String = code

    companion object {
        fun from(code: String) = values().firstOrNull { it.code == code } ?: UNHANDLED_EXCEPTION
    }
}
