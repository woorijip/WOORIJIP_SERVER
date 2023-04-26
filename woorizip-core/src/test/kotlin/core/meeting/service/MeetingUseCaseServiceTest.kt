package core.meeting.service

import core.meeting.createMeeting
import core.meeting.spi.CommandMeetingPort
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class MeetingUseCaseServiceTest : DescribeSpec({
    val commandMeetingPort: CommandMeetingPort = mockk()

    val meetingUseCaseService = MeetingUseCaseServiceImpl(commandMeetingPort)

    describe("create를 호출했을 때") {
        val createdMeeting = createMeeting()

        coEvery { commandMeetingPort.saveMeeting(any()) } returns createdMeeting

        it("저장된 Meeting 객체를 반환한다.") {
            val result = meetingUseCaseService.create(createdMeeting)

            result shouldBe createdMeeting
        }
    }
})
