package persistence.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import persistence.member.model.InterestCategoryTable
import persistence.member.model.MemberTable
import java.util.Properties

object DatabaseConnector {
    lateinit var database: Database

    private const val DB_PREFIX = "database"
    const val DB_URL = "$DB_PREFIX.url"
    const val DB_USER = "$DB_PREFIX.user"
    const val DB_PASSWORD = "$DB_PREFIX.password"
    const val DB_DRIVER = "$DB_PREFIX.driver"

    operator fun invoke(properties: Properties) {
        database = Database.connect(
            HikariDataSource(
                HikariConfig().apply {
                    jdbcUrl = properties.getProperty(DB_URL)
                    username = properties.getProperty(DB_USER)
                    password = properties.getProperty(DB_PASSWORD)
                    driverClassName = properties.getProperty(DB_DRIVER)
                }
            )
        )

        val tables: Array<Table> = arrayOf(
            MemberTable,
            InterestCategoryTable
        )

        transaction(database) {
            addLogger(StdOutSqlLogger)
            tables.run(SchemaUtils::create)
        }
    }
}
