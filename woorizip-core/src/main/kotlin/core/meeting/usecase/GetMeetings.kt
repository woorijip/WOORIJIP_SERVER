package core.meeting.usecase

import core.meeting.model.Category
import core.meeting.model.WeekType
import core.meeting.service.MeetingService
import core.meeting.usecase.result.MeetingOutput
import core.member.service.MemberService
import core.outport.TransactionPort

class GetMeetings(
    private val memberService: MemberService,
    private val meetingService: MeetingService,
    private val txPort: TransactionPort
) {
    suspend operator fun invoke(input: Input, currentMemberId: Long): List<MeetingOutput> {
        return txPort.withNewTransaction {
            val currentMember = memberService.getMemberById(currentMemberId) // TODO 동네 필터

            val meetings = meetingService.getMeetings(input.categories, input.weekType, input.name)

            return@withNewTransaction meetings.map(MeetingOutput::of)
        }
    }

    class Input(
        categories: List<Category>?,
        weekType: WeekType?,
        name: String?,
        town: String?
    ) {
        val categories: List<Category> = categories ?: emptyList()

        val weekType: WeekType = weekType ?: WeekType.ALL

        val name: String = name ?: ""

        val town: String? = town
    }
}
