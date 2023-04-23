package persistence.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import persistence.meeting.model.MeetingCategoryTable
import persistence.meeting.model.MeetingImageTable
import persistence.meeting.model.MeetingScheduleTable
import persistence.meeting.model.MeetingTable
import persistence.member.model.InterestCategoryTable
import persistence.member.model.MemberTable

lateinit var database: Database

private const val DB_PREFIX = "database"
private const val DB_URL = "$DB_PREFIX.url"
private const val DB_USER = "$DB_PREFIX.user"
private const val DB_PASSWORD = "$DB_PREFIX.password"
private const val DB_DRIVER = "$DB_PREFIX.driver"

fun Application.databaseConnector() {
    database = Database.connect(
        HikariDataSource(
            HikariConfig().apply {
                jdbcUrl = this@databaseConnector.environment.config.property(DB_URL).getString()
                username = this@databaseConnector.environment.config.property(DB_USER).getString()
                password = this@databaseConnector.environment.config.property(DB_PASSWORD).getString()
                driverClassName = this@databaseConnector.environment.config.property(DB_DRIVER).getString()
            }
        )
    )

    val tables: Array<Table> = arrayOf(
        MemberTable,
        InterestCategoryTable,
        MeetingTable,
        MeetingImageTable,
        MeetingScheduleTable,
        MeetingCategoryTable
    )

    transaction(database) {
        addLogger(StdOutSqlLogger)
        tables.run(SchemaUtils::create)
    }
}
