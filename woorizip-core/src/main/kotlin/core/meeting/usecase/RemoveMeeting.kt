package core.meeting.usecase

import core.meeting.service.MeetingService
import core.outport.TransactionPort

class RemoveMeeting(
    private val meetingService: MeetingService,
    private val txPort: TransactionPort
) {

    suspend operator fun invoke(meetingId: Long) {
        txPort.withNewTransaction {
            meetingService.removeMeeting(meetingId)
        }
    }
}
