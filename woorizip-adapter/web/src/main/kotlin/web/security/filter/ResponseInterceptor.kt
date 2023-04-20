package web.security.filter

import core.context.MemberContextService
import io.ktor.serialization.Configuration
import io.ktor.server.application.ApplicationCallPipeline
import io.ktor.server.application.BaseApplicationPlugin
import io.ktor.server.response.ApplicationSendPipeline
import io.ktor.util.AttributeKey

class ResponseInterceptor private constructor() {
    companion object Plugin : BaseApplicationPlugin<ApplicationCallPipeline, Configuration, ResponseInterceptor> {
        override val key = AttributeKey<ResponseInterceptor>("ResponseInterceptor")

        override fun install(
            pipeline: ApplicationCallPipeline,
            configure: Configuration.() -> Unit
        ): ResponseInterceptor {
            val plugin = ResponseInterceptor()

            pipeline.sendPipeline.intercept(ApplicationSendPipeline.After) {
                MemberContextService.clear()
            }

            return plugin
        }
    }
}
