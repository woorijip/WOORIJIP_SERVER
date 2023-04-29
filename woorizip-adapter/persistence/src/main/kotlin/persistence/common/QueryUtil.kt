package persistence.common

import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.ResultRow

internal fun Query.toResultRow(): List<ResultRow> {
    return this.map { it }
}
