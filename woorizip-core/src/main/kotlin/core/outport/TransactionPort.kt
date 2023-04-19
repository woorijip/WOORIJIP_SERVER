package core.outport

import core.annotation.RequiresTransactionContext

interface TransactionPort {
    suspend fun <T> withNewTransaction(readOnly: Boolean = false, block: suspend () -> T): T

    @RequiresTransactionContext
    suspend fun <T> withExistingTransaction(block: suspend () -> T): T

    @RequiresTransactionContext
    suspend fun <T> withTransaction(readOnly: Boolean = false, block: suspend () -> T): T
}
