package core.member.service

import core.member.model.InterestCategory
import core.member.model.Member
import core.member.spi.CommandMemberPort

interface CommandMemberService {
    suspend fun saveMember(member: Member): Member
    suspend fun saveInterestCategories(
        memberId: Int,
        categoryNames: List<String>
    ): List<InterestCategory>
}

class CommandMemberServiceImpl(private val commandMemberPort: CommandMemberPort) : CommandMemberService {
    override suspend fun saveMember(member: Member): Member {
        return commandMemberPort.saveMember(member)
    }

    override suspend fun saveInterestCategories(
        memberId: Int,
        categoryNames: List<String>
    ): List<InterestCategory> {
        val interestCategories = categoryNames.map { InterestCategory(it, memberId) }
        return commandMemberPort.saveAllInterestCategories(memberId, interestCategories)
    }
}
