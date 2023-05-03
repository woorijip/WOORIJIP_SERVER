package web.config

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import com.fasterxml.jackson.module.kotlin.kotlinModule
import io.ktor.serialization.jackson.jackson
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")

fun Application.serialization() {
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            registerModules(
                kotlinModule(),
                JavaTimeModule().apply {
                    addSerializer(LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME))
                    addDeserializer(
                        LocalDateTime::class.java,
                        LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME)
                    )

                    addSerializer(LocalDateSerializer(DateTimeFormatter.ISO_DATE))
                    addDeserializer(LocalDate::class.java, LocalDateDeserializer(DateTimeFormatter.ISO_DATE))

                    addSerializer(LocalTimeSerializer(timeFormat))
                    addDeserializer(LocalTime::class.java, LocalTimeDeserializer(timeFormat))
                }
            )
        }
    }
}
