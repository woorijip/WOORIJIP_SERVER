package core.meeting.service

import core.meeting.createMeeting
import core.meeting.exception.MeetingNotFoundException
import core.meeting.spi.CommandMeetingPort
import core.meeting.spi.QueryMeetingPort
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class CommandMeetingServiceTest : DescribeSpec({
    val commandMeetingPort: CommandMeetingPort = mockk()
    val queryMeetingPort: QueryMeetingPort = mockk()

    val commandMeetingService = CommandMeetingServiceImpl(commandMeetingPort, queryMeetingPort)

    describe("createMeeting을 호출했을 때") {
        val createdMeeting = createMeeting()

        coEvery { commandMeetingPort.createMeeting(any()) } returns createdMeeting

        it("저장된 Meeting 객체를 반환한다.") {
            val result = commandMeetingService.createMeeting(createdMeeting)

            result shouldBe createdMeeting
        }
    }

    describe("removeMeeting을 호출했을 때") {
        val meetingId = 1L
        val meeting = createMeeting(id = meetingId)

        context("모임이 존재하지 않으면") {
            coEvery { queryMeetingPort.getMeetingById(meetingId) } returns null

            it("MeetingNotFoundException 예외를 던진다.") {
                shouldThrow<MeetingNotFoundException> {
                    commandMeetingService.removeMeeting(meetingId)
                }
            }
        }

        it("Meeting 객체를 삭제한다.") {
            coEvery { queryMeetingPort.getMeetingById(meetingId) } returns meeting
            coEvery { commandMeetingPort.removeMeeting(any()) } returns Unit

            shouldNotThrowAny {
                commandMeetingService.removeMeeting(meetingId)
            }
        }
    }
})
