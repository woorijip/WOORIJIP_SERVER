package core.member.spi

import core.member.model.InterestCategory
import core.member.model.Member

interface CommandMemberPort {
    suspend fun createMember(member: Member): Member
    suspend fun createAllInterestCategories(
        memberId: Long,
        interestCategories: List<InterestCategory>
    ): List<InterestCategory>
}
