package core.member.service

import core.member.model.Category
import core.member.model.InterestCategory
import core.member.model.Member
import core.member.spi.CommandMemberPort

interface CommandMemberService {
    suspend fun signUp(member: Member): Member
    suspend fun saveMember(member: Member): Member
    suspend fun saveInterestCategories(
        memberId: Int,
        categories: List<Category>
    ): List<InterestCategory>
}

class CommandMemberServiceImpl(private val commandMemberPort: CommandMemberPort) : CommandMemberService {
    override suspend fun signUp(member: Member): Member {
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
