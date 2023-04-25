package core.member.service

import core.member.exception.AlreadyExistsException
import core.member.model.Member
import core.member.spi.CommandMemberPort
import core.member.spi.QueryMemberPort

interface CommandMemberService {
    suspend fun signUp(member: Member): Member
}

class CommandMemberServiceImpl(
    private val commandMemberPort: CommandMemberPort,
    private val queryMemberPort: QueryMemberPort
) : CommandMemberService {
    override suspend fun signUp(member: Member): Member {
        if (queryMemberPort.existsMemberByEmail(member.email.value)) {
            throw AlreadyExistsException(member.email.value)
        }

        if (queryMemberPort.existsMemberByPhoneNumber(member.phoneNumber)) {
            throw AlreadyExistsException(member.phoneNumber)
        }

        return commandMemberPort.saveMember(
            member.copy(password = member.password.encode())
        )
    }
}
