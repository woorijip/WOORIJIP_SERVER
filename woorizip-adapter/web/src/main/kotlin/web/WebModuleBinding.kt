package web

import core.outport.TokenPort
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import web.member.MemberRestApi
import web.security.token.TokenAdapter

val webModule: Module
    get() = module {
        includes(apiModule, securityModule)
    }

internal val apiModule = module {
    // health check
    singleOf(::HealthCheckApi) bind Api::class

    // member
    singleOf(::MemberRestApi) bind Api::class
}

val securityModule: Module
    get() = module {
        includes(tokenModule)
    }

internal val tokenModule = module {
    singleOf(::TokenAdapter) bind TokenPort::class
}
