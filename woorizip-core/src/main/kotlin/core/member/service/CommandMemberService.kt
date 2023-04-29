package core.member.service

import core.meeting.model.Category
import core.member.model.InterestCategory
import core.member.model.Member
import core.member.spi.CommandMemberPort

interface CommandMemberService {
    suspend fun saveMember(member: Member): Member
    suspend fun saveInterestCategories(
        memberId: Long,
        categories: List<Category>
    ): List<InterestCategory>
}

class CommandMemberServiceImpl(
    private val commandMemberPort: CommandMemberPort
) : CommandMemberService {
    override suspend fun saveMember(member: Member): Member {
        return commandMemberPort.saveMember(member)
    }

    override suspend fun saveInterestCategories(
        memberId: Long,
        categories: List<Category>
    ): List<InterestCategory> {
        val interestCategoryModels = categories.map { InterestCategory(category = it, memberId = memberId) }
        return commandMemberPort.saveAllInterestCategories(memberId, interestCategoryModels)
    }
}
