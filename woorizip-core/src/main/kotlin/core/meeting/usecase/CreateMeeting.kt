package core.meeting.usecase

import core.meeting.model.Category
import core.meeting.model.Meeting
import core.meeting.model.MeetingCategory
import core.meeting.model.MeetingSchedule
import core.meeting.service.MeetingService
import core.meeting.usecase.result.MeetingOutput
import core.member.service.MemberService
import core.outport.TransactionPort
import java.time.LocalDate
import java.time.LocalTime

class CreateMeeting(
    private val memberService: MemberService,
    private val meetingService: MeetingService,
    private val txPort: TransactionPort
) {

    suspend operator fun invoke(input: Input, currentMemberId: Long): MeetingOutput {
        return txPort.withNewTransaction {
            val currentMember = memberService.getMemberById(currentMemberId)

            val savedMeeting = meetingService.create(
                input.toDomain(currentMember.id)
            )

            return@withNewTransaction MeetingOutput.of(savedMeeting)
        }
    }

    data class Input(
        val name: String,
        val introduction: String,
        val thumbnail: String,
        val location: String,
        val spaceType: Meeting.Space.SpaceType,
        val spaceImages: List<String>,
        val description: String,
        val meetingSchedules: List<ScheduleInput>,
        val categories: List<Category>,
    ) {
        data class ScheduleInput(
            val date: LocalDate,
            val time: LocalTime,
            val maxMember: Int
        )

        fun toDomain(createMemberId: Long): Meeting {
            return Meeting(
                name = name,
                introduction = introduction,
                thumbnail = thumbnail,
                space = Meeting.Space(
                    location = location,
                    type = spaceType,
                    images = spaceImages.map { Meeting.Space.MeetingImage(image = it) }
                ),
                description = description,
                meetingSchedules = meetingSchedules.map {
                    MeetingSchedule(
                        date = it.date,
                        time = it.time,
                        maxMember = it.maxMember
                    )
                },
                categories = categories.map { MeetingCategory(category = it) },
                createMemberId = createMemberId
            )
        }
    }
}
