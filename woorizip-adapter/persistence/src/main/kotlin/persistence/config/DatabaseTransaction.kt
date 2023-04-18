package persistence.config

import com.zaxxer.hikari.util.IsolationLevel
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

suspend fun <T> dbQuery(
    database: Database? = null,
    block: suspend (Transaction) -> T
): T {
    return newSuspendedTransaction(db = database, context = Dispatchers.IO) {
        addLogger(StdOutSqlLogger)
        block(this)
    }
}

suspend fun <T> dbReadOnlyQuery(
    database: Database? = null,
    block: suspend (Transaction) -> T
): T {
    return newSuspendedTransaction(
        db = database,
        context = Dispatchers.IO,
        transactionIsolation = IsolationLevel.TRANSACTION_REPEATABLE_READ.levelId
    ) {
        addLogger(StdOutSqlLogger)
        block(this)
    }
}
