package security

import core.outport.TokenPort
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import security.token.TokenAdapter

val securityModule: Module
    get() = module {
        includes(tokenModule)
    }

internal val tokenModule = module {
    singleOf(::TokenAdapter) bind TokenPort::class
}
