package core.member.service

import core.meeting.model.Category
import core.member.exception.AlreadyExistsException
import core.member.model.InterestCategory
import core.member.model.Member
import core.member.spi.CommandMemberPort
import core.member.spi.QueryMemberPort

interface CommandMemberService {
    suspend fun signUp(member: Member): Member
    suspend fun saveMember(member: Member): Member
    suspend fun saveInterestCategories(
        memberId: Int,
        categories: List<Category>
    ): List<InterestCategory>
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

    override suspend fun saveMember(member: Member): Member {
        return commandMemberPort.saveMember(member)
    }

    override suspend fun saveInterestCategories(
        memberId: Int,
        categories: List<Category>
    ): List<InterestCategory> {
        val interestCategoryModels = categories.map { InterestCategory(it, memberId) }
        return commandMemberPort.saveAllInterestCategories(memberId, interestCategoryModels)
    }
}
