package persistence

import core.meeting.spi.CommandMeetingPort
import core.meeting.spi.QueryMeetingPort
import core.member.spi.CommandMemberPort
import core.member.spi.QueryMemberPort
import core.outport.TransactionPort
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.binds
import org.koin.dsl.module
import persistence.config.TransactionConfig
import persistence.meeting.MeetingPersistenceAdapter
import persistence.meeting.repository.MeetingRepository
import persistence.meeting.repository.MeetingRepositoryImpl
import persistence.member.MemberPersistenceAdapter
import persistence.member.repository.MemberRepository
import persistence.member.repository.MemberRepositoryImpl

val persistenceModule: Module
    get() = module {
        includes(repositoryModule, adapterModule)
    }

internal val repositoryModule = module {
    singleOf(::MemberRepositoryImpl) bind MemberRepository::class
    singleOf(::MeetingRepositoryImpl) bind MeetingRepository::class
}

internal val adapterModule = module {
    singleOf(::MemberPersistenceAdapter) binds arrayOf(QueryMemberPort::class, CommandMemberPort::class)
    singleOf(::MeetingPersistenceAdapter) binds arrayOf(QueryMeetingPort::class, CommandMeetingPort::class)
    singleOf(::TransactionConfig) bind TransactionPort::class
}
