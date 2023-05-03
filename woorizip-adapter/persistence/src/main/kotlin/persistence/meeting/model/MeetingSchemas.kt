package persistence.meeting.model

import core.meeting.model.Category
import core.meeting.model.Meeting
import core.meeting.model.MeetingCategory
import core.meeting.model.MeetingSchedule
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.time
import persistence.common.BaseTable
import persistence.member.model.MemberTable

object MeetingTable : BaseTable("tbl_meeting") {
    val name = varchar("name", length = Meeting.NAME_MAX_LENGTH)
    val introduction = varchar("introduction", length = Meeting.INTRODUCTION_MAX_LENGTH)
    val thumbnailImage = varchar("thumbnail_image", length = 255)
    val location = text("location")
    val spaceType = enumerationByName<Meeting.Space.SpaceType>("space_type", length = 15)
    val description = varchar("description", length = 255)
    val meetingCount = integer("meeting_count")
    val createMemberId = reference("create_member_id", MemberTable.id)
}

object MeetingImageTable : BaseTable("tbl_meeting_image") {
    val image = varchar("image", length = 255)
    val meetingId = reference("meeting_id", MeetingTable.id)
}

object MeetingScheduleTable : BaseTable("tbl_meeting_schedule") {
    val date = date("date")
    val meetingId = reference("meeting_id", MeetingTable.id)
    val time = time("time")
    val maxMember = integer("max_member")
}

object MeetingCategoryTable : BaseTable("tbl_meeting_category") {
    val categoryName = enumerationByName<Category>("category_name", length = 20)
    val meetingId = reference("meeting_id", MeetingTable.id)
}

internal fun MeetingTable.toDomain(
    meetingRow: ResultRow?,
    imageRow: List<ResultRow>,
    scheduleRow: List<ResultRow>,
    categoryRow: List<ResultRow>
): Meeting? {
    return meetingRow?.let {
        toDomainNotNull(
            meetingRow = it,
            imageRow = imageRow,
            scheduleRow = scheduleRow,
            categoryRow = categoryRow
        )
    }
}

internal fun MeetingTable.toDomainNotNull(
    meetingRow: ResultRow,
    imageRow: List<ResultRow>,
    scheduleRow: List<ResultRow>,
    categoryRow: List<ResultRow>
): Meeting {
    return Meeting(
        id = meetingRow[this.id].value,
        name = meetingRow[this.name],
        introduction = meetingRow[this.introduction],
        thumbnail = meetingRow[this.thumbnailImage],
        space = Meeting.Space(
            type = meetingRow[this.spaceType],
            location = meetingRow[this.location],
            images = imageRow.map { image ->
                Meeting.Space.MeetingImage(
                    id = image[MeetingImageTable.id].value,
                    image = image[MeetingImageTable.image]
                )
            }
        ),
        description = meetingRow[this.description],
        meetingSchedules = scheduleRow.map { schedule ->
            MeetingSchedule(
                id = schedule[MeetingScheduleTable.id].value,
                date = schedule[MeetingScheduleTable.date],
                time = schedule[MeetingScheduleTable.time],
                maxMember = schedule[MeetingScheduleTable.maxMember]
            )
        },
        categories = categoryRow.map { category ->
            MeetingCategory(
                id = category[MeetingCategoryTable.id].value,
                category = category[MeetingCategoryTable.categoryName]
            )
        },
        meetingCount = meetingRow[this.meetingCount],
        createMemberId = meetingRow[this.createMemberId].value,
        createdAt = meetingRow[this.createdAt],
        updatedAt = meetingRow[this.updatedAt]
    )
}
