package core.member.service

import core.member.exception.AlreadyExistsException
import core.member.model.Member
import core.member.model.Password
import core.member.spi.CommandMemberPort
import core.member.spi.QueryMemberPort

interface MemberUseCaseService {
    suspend fun signUp(member: Member): Member
    suspend fun signIn(member: Member, rawPassword: Password)
}

class MemberUseCaseServiceImpl(
    private val queryMemberPort: QueryMemberPort,
    private val commandMemberPort: CommandMemberPort
) : MemberUseCaseService {
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

    override suspend fun signIn(member: Member, rawPassword: Password) {
        member.checkIsSamePassword(rawPassword)
    }
}
