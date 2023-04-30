package core.meeting.service

import core.meeting.createMeeting
import core.meeting.exception.MeetingNotFoundException
import core.meeting.spi.QueryMeetingPort
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class QueryMeetingServiceTest : DescribeSpec({
    val queryMeetingPort: QueryMeetingPort = mockk()

    val queryMeetingService = QueryMeetingServiceImpl(queryMeetingPort)

    describe("getMeetingById를 호출했을 때") {
        val meetingId = 1L
        val meeting = createMeeting(id = meetingId)

        context("모임이 존재한다면") {
            coEvery { queryMeetingPort.getMeetingById(meetingId) } returns meeting

            it("해당하는 모임을 반환한다.") {
                val result = queryMeetingService.getMeetingById(meetingId)

                result shouldBe meeting
            }
        }

        context("모임이 존재하지 않으면") {
            coEvery { queryMeetingPort.getMeetingById(meetingId) } returns null

            it("MeetingNotFoundException 예외를 던진다.") {
                shouldThrow<MeetingNotFoundException> {
                    queryMeetingService.getMeetingById(meetingId)
                }
            }
        }
    }
})
