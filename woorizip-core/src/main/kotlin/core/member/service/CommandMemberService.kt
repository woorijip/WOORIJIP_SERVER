package core.member.service

import core.member.model.Member
import core.member.spi.CommandMemberPort

interface CommandMemberService {
    suspend fun save(member: Member): Member
}

class CommandMemberServiceImpl(private val commandMemberPort: CommandMemberPort) : CommandMemberService {
    override suspend fun save(member: Member): Member {
        return commandMemberPort.save(member)
    }
}
