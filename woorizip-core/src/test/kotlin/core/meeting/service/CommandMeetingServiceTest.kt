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

    describe("saveMeeting를 호출했을 때") {
        val savedMeeting = createMeeting()

        coEvery { commandMeetingPort.saveMeeting(any()) } returns savedMeeting

        it("저장된 Meeting 객체를 반환한다.") {
            val result = commandMeetingService.saveMeeting(savedMeeting)

            savedMeeting shouldBe result
        }
    }
})
