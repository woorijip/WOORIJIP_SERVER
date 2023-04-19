package persistence

import core.member.spi.CommandMemberPort
import core.member.spi.QueryMemberPort
import core.outport.TransactionPort
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.binds
import org.koin.dsl.module
import persistence.config.TransactionConfig
import persistence.member.MemberPersistenceAdapter
import persistence.member.repository.MemberRepository
import persistence.member.repository.MemberRepositoryImpl

val persistenceModule: Module
    get() = module {
        includes(repositoryModule, adapterModule)
    }

internal val repositoryModule = module {
    singleOf(::MemberRepositoryImpl) bind MemberRepository::class
}

internal val adapterModule = module {
    singleOf(::MemberPersistenceAdapter) binds arrayOf(QueryMemberPort::class, CommandMemberPort::class)
    singleOf(::TransactionConfig) bind TransactionPort::class
}
