package core.member.service

import core.member.model.InterestCategory
import core.member.model.Member
import core.member.spi.CommandMemberPort

interface CommandMemberService {
    suspend fun saveMember(member: Member): Member
    suspend fun saveInterestCategories(
        memberId: Int,
        interestCategories: List<InterestCategory>
    ): List<InterestCategory>
}

class CommandMemberServiceImpl(private val commandMemberPort: CommandMemberPort) : CommandMemberService {
    override suspend fun saveMember(member: Member): Member {
        return commandMemberPort.saveMember(member)
    }

    override suspend fun saveInterestCategories(
        memberId: Int,
        interestCategories: List<InterestCategory>
    ): List<InterestCategory> {
        return commandMemberPort.saveAllInterestCategories(memberId, interestCategories)
    }
}
