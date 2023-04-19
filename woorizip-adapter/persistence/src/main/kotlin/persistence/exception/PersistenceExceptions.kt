package persistence.exception

import common.exception.BaseException
import org.jetbrains.exposed.exceptions.ExposedSQLException

object SqlState {
    const val DUPLICATE_KEY_VALUE = "23000"
}

internal fun ExposedSQLException.throwAsDomainException(): Nothing {
    when (this.sqlState) {
        SqlState.DUPLICATE_KEY_VALUE -> throw DuplicateKeyValueException(message = this.message ?: this.toString())
        else -> throw this
    }
}

class DuplicateKeyValueException(
    override val code: String = PersistenceErrorCode.DUPLICATE_KEY_VALUE.code,
    override val message: String = PersistenceErrorCode.DUPLICATE_KEY_VALUE.message,
) : BaseException.ConflictException(code, message)
