package core.member.service

import core.meeting.model.Category
import core.member.exception.AlreadyExistsException
import core.member.model.InterestCategory
import core.member.model.Member
import core.member.spi.CommandMemberPort
import core.member.spi.QueryMemberPort

interface CommandMemberService {
    suspend fun createMember(member: Member): Member
    suspend fun createInterestCategories(
        memberId: Long,
        categories: List<Category>
    ): List<InterestCategory>
}

class CommandMemberServiceImpl(
    private val queryMemberPort: QueryMemberPort,
    private val commandMemberPort: CommandMemberPort
) : CommandMemberService {
    override suspend fun createMember(member: Member): Member {
        if (queryMemberPort.existsMemberByEmail(member.email.value)) {
            throw AlreadyExistsException(member.email.value)
        }

        if (queryMemberPort.existsMemberByPhoneNumber(member.phoneNumber)) {
            throw AlreadyExistsException(member.phoneNumber)
        }

        return commandMemberPort.createMember(
            member.copy(password = member.password.encode())
        )
    }

    override suspend fun createInterestCategories(
        memberId: Long,
        categories: List<Category>
    ): List<InterestCategory> {
        val interestCategoryModels = categories.map { InterestCategory(category = it, memberId = memberId) }
        return commandMemberPort.createAllInterestCategories(memberId, interestCategoryModels)
    }
}
