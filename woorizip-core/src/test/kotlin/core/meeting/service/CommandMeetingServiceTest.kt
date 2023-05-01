package core.meeting.service

import core.meeting.createMeeting
import core.meeting.spi.CommandMeetingPort
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class CommandMeetingServiceTest : DescribeSpec({
    val commandMeetingPort: CommandMeetingPort = mockk()

    val commandMeetingService = CommandMeetingServiceImpl(commandMeetingPort)

    describe("createMeeting을 호출했을 때") {
        val createdMeeting = createMeeting()

        coEvery { commandMeetingPort.createMeeting(any()) } returns createdMeeting

        it("저장된 Meeting 객체를 반환한다.") {
            val result = commandMeetingService.createMeeting(createdMeeting)

            result shouldBe createdMeeting
        }
    }
})
