package persistence.config

import org.jetbrains.exposed.sql.FieldSet
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.select

fun <T : FieldSet> T.exists(where: Op<Boolean>): Boolean {
    return this.select(where).empty()
}
