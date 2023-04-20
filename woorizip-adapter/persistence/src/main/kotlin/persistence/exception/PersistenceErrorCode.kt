package persistence.exception

import common.exception.BaseException
import common.exception.ErrorCode

enum class PersistenceErrorCode(
    override val sequence: Int,
    override val message: String
) : ErrorCode {

    DUPLICATE_KEY_VALUE(1, "Duplicate key value violates unique constraint")

    ;

    val code = getCode(prefix = "PERSISTENCE")

    companion object {
        fun from(code: String) = values().firstOrNull { it.code == code }
            ?: throw BaseException.UnhandledException(message = "해당 에러 코드는 존재하지 않습니다.")
    }
}
