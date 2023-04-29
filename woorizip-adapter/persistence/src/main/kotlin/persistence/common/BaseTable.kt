package persistence.common

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

abstract class BaseTable(name: String, idName: String = "id") : LongIdTable(name, idName) {
    val createdAt = datetime("created_at").clientDefault(LocalDateTime::now)
    val updatedAt = datetime("updated_at").clientDefault(LocalDateTime::now)
}
