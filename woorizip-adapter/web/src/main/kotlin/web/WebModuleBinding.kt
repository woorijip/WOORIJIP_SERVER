package web

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import web.member.MemberRestApi

val webModule: Module
    get() = module {
        includes(apiModule)
    }

internal val apiModule = module {
    // health check
    singleOf(::HealthCheckApi) bind Api::class

    // member
    singleOf(::MemberRestApi) bind Api::class
}
