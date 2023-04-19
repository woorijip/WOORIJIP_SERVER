package core.member.spi

import core.member.model.InterestCategory
import core.member.model.Member

interface CommandMemberPort {
    suspend fun saveMember(member: Member): Member
    suspend fun saveAllInterestCategories(
        memberId: Int,
        interestCategories: List<InterestCategory>
    ): List<InterestCategory>
}
