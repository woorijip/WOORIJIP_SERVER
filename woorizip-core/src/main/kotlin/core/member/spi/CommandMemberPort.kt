package core.member.spi

import core.member.model.Member

interface CommandMemberPort {
    suspend fun save(member: Member): Member
}
