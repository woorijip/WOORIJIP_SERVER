package web.exception

import common.exception.BaseException
import common.exception.ErrorCode

enum class WebErrorCode(
    override val sequence: Int,
    override val message: String
) : ErrorCode {

    INVALID_QUERY_PARAM(1, "Invalid query parameter"),
    INVALID_PATH_PARAM(2, "Invalid path parameter")

    ;

    val code = getCode(prefix = "WEB")

    companion object {
        fun from(code: String) = values().firstOrNull { it.code == code }
            ?: throw BaseException.UnhandledException(message = "해당 에러 코드는 존재하지 않습니다.")
    }
}
