package core.meeting.usecase

import core.meeting.service.MeetingService
import core.meeting.usecase.result.MeetingOutput
import core.outport.TransactionPort

class GetMeetingDetails(
    private val meetingService: MeetingService,
    private val txPort: TransactionPort
) {

    suspend operator fun invoke(meetingId: Long): MeetingOutput {
        return txPort.withNewTransaction {
            val meeting = meetingService.getMeetingById(meetingId)

            return@withNewTransaction MeetingOutput.of(meeting).withReviews()
        }
    }
}
