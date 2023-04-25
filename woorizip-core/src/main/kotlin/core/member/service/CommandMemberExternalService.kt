package core.member.service

import core.meeting.model.Category
import core.member.model.InterestCategory
import core.member.model.Member
import core.member.spi.CommandMemberPort

interface CommandMemberExternalService {
    suspend fun saveMember(member: Member): Member
    suspend fun saveInterestCategories(
        memberId: Int,
        categories: List<Category>
    ): List<InterestCategory>
}

class CommandMemberExternalServiceImpl(
    private val commandMemberPort: CommandMemberPort
) : CommandMemberExternalService {
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
