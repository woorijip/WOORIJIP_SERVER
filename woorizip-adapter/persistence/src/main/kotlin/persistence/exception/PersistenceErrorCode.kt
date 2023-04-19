package persistence.exception

import common.exception.BaseErrorCode
import common.exception.ErrorCode

enum class PersistenceErrorCode(
    override val sequence: Int,
    override val message: String
) : ErrorCode {

    DUPLICATE_KEY_VALUE(1, "Duplicate key value violates unique constraint")

    ;

    val code = getCode(prefix = "PERSISTENCE")

    companion object {
        fun from(code: String) = values().firstOrNull { it.code == code } ?: BaseErrorCode.UNHANDLED_EXCEPTION
    }
}
