package persistence.config

import com.zaxxer.hikari.util.IsolationLevel
import common.exception.BaseException
import core.annotation.RequiresTransactionContext
import core.outport.TransactionPort
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransaction
import org.jetbrains.exposed.sql.transactions.transactionManager
import persistence.exception.throwAsDomainException

class TransactionConfig : TransactionPort {
    override suspend fun <T> withNewTransaction(
        readOnly: Boolean,
        block: suspend () -> T
    ): T {
        return try {
            if (!readOnly) {
                newSuspendedTransaction(db = database, context = Dispatchers.IO) {
                    addLogger(StdOutSqlLogger)
                    block()
                }
            } else {
                newSuspendedTransaction(
                    db = database,
                    context = Dispatchers.IO,
                    transactionIsolation = IsolationLevel.TRANSACTION_REPEATABLE_READ.levelId
                ) {
                    addLogger(StdOutSqlLogger)
                    block()
                }
            }
        } catch (e: ExposedSQLException) {
            e.throwAsDomainException()
        }
    }

    @RequiresTransactionContext
    override suspend fun <T> withExistingTransaction(
        block: suspend () -> T,
    ): T {
        val tx = database.transactionManager.currentOrNull()

        if (tx == null) {
            throw BaseException.UnhandledException(
                message = "withExistingTransaction(): no current transaction in context"
            )
        } else if (tx.connection.isClosed) {
            throw BaseException.UnhandledException(
                message = "withExistingTransaction(): current transaction is closed"
            )
        }

        return try {
            tx.suspendedTransaction(context = Dispatchers.IO) {
                addLogger(StdOutSqlLogger)
                block()
            }
        } catch (e: ExposedSQLException) {
            e.throwAsDomainException()
        }
    }

    @RequiresTransactionContext
    override suspend fun <T> withTransaction(
        readOnly: Boolean,
        block: suspend () -> T,
    ): T {
        val tx = TransactionManager.currentOrNull()

        return try {
            if (tx == null || tx.connection.isClosed) {
                withNewTransaction(readOnly, block)
            } else {
                withExistingTransaction(block)
            }
        } catch (e: ExposedSQLException) {
            e.throwAsDomainException()
        }
    }
}
