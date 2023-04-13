package common.exception

enum class ErrorCode(
    val code: String
) {
    // MEMBER
    MEMBER_NOT_FOUND("M-001"),

    // UNHANDLED
    UNHANDLED_EXCEPTION("-1")

    ;

    companion object {
        fun from(code: String) = values().firstOrNull { it.code == code } ?: UNHANDLED_EXCEPTION
    }
}