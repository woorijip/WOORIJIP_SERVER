package main

import core.member.service.CheckMemberService
import core.member.service.CheckMemberServiceImpl
import core.member.service.CommandMemberService
import core.member.service.CommandMemberServiceImpl
import core.member.service.MemberService
import core.member.service.QueryMemberService
import core.member.service.QueryMemberServiceImpl
import core.member.spi.CommandMemberPort
import core.member.spi.QueryMemberPort
import core.member.usecase.SignUp
import io.ktor.server.application.Application
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.binds
import org.koin.dsl.module
import persistence.member.MemberPersistenceAdapter
import persistence.member.repository.MemberRepository
import persistence.member.repository.MemberRepositoryImpl
import web.Api
import web.HealthCheckApi
import web.member.MemberRestApi

fun Application.binding() {
    val healthCheck: List<Module> = listOf(
        module {
            singleOf(::HealthCheckApi) bind Api::class
        }
    )

    val member: List<Module> = listOf(
        module {
            // core
            singleOf(::MemberService)
            singleOf(::QueryMemberServiceImpl) bind QueryMemberService::class
            singleOf(::CommandMemberServiceImpl) bind CommandMemberService::class
            singleOf(::CheckMemberServiceImpl) bind CheckMemberService::class

            singleOf(::SignUp)

            // web
            singleOf(::MemberRestApi) bind Api::class

            // persistence
            singleOf(::MemberRepositoryImpl) bind MemberRepository::class
            singleOf(::MemberPersistenceAdapter) binds arrayOf(QueryMemberPort::class, CommandMemberPort::class)
        }
    )

    stopKoin()
    startKoin {
        modules(
            healthCheck + member
        )
    }
}
