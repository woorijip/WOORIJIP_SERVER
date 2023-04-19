package core

import core.member.service.CheckMemberService
import core.member.service.CheckMemberServiceImpl
import core.member.service.CommandMemberService
import core.member.service.CommandMemberServiceImpl
import core.member.service.MemberService
import core.member.service.QueryMemberService
import core.member.service.QueryMemberServiceImpl
import core.member.usecase.SignUp
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreModule: Module
    get() = module {
        includes(serviceModule, useCaseModule)
    }

internal val serviceModule = module {
    // member
    singleOf(::MemberService)
    singleOf(::QueryMemberServiceImpl) bind QueryMemberService::class
    singleOf(::CommandMemberServiceImpl) bind CommandMemberService::class
    singleOf(::CheckMemberServiceImpl) bind CheckMemberService::class
}

internal val useCaseModule = module {
    // member
    singleOf(::SignUp)
}
