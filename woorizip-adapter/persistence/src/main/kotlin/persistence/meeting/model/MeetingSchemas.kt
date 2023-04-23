package persistence.meeting.model

import core.meeting.model.Category
import core.meeting.model.Meeting
import core.meeting.model.MeetingCategory
import core.meeting.model.MeetingSchedule
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.javatime.time
import persistence.member.model.MemberTable
import java.time.LocalDateTime

object MeetingTable : IntIdTable("tbl_meeting") {
    val name = varchar("name", length = Meeting.NAME_MAX_LENGTH)
    val introduction = varchar("introduction", length = Meeting.INTRODUCTION_MAX_LENGTH)
    val thumbnailImage = varchar("thumbnail_image", length = 255)
    val location = text("location")
    val spaceType = enumerationByName<Meeting.Space.SpaceType>("space_type", length = 15)
    val description = varchar("description", length = 255)
    val createMemberId = reference("create_member_id", MemberTable.id)
    val createdAt = datetime("created_at").clientDefault(LocalDateTime::now)
    val updatedAt = datetime("updated_at").clientDefault(LocalDateTime::now)
}

object MeetingImageTable : Table("tbl_meeting_image") {
    val sequence = integer("sequence")
    val meetingId = reference("meeting_id", MeetingTable)
    val image = varchar("image", length = 255)

    override val primaryKey = PrimaryKey(sequence, meetingId)
}

object MeetingScheduleTable : Table("tbl_meeting_schedule") {
    val date = date("date")
    val meetingId = reference("meeting_id", MeetingTable)
    val time = time("time")
    val maxMember = integer("max_member")

    override val primaryKey = PrimaryKey(date, meetingId)
}

object MeetingCategoryTable : Table("tbl_meeting_category") {
    val categoryName = enumerationByName<Category>("category_name", length = 20)
    val meetingId = reference("meeting_id", MeetingTable)

    override val primaryKey = PrimaryKey(categoryName, meetingId)
}

internal fun MeetingTable.toDomain(
    meetingRow: ResultRow?,
    imageRow: List<ResultRow>,
    scheduleRow: List<ResultRow>,
    categoryRow: List<ResultRow>
): Meeting? {
    return meetingRow?.let {
        Meeting(
            id = meetingRow[this.id].value,
            name = meetingRow[this.name],
            introduction = meetingRow[this.introduction],
            thumbnail = meetingRow[this.thumbnailImage],
            space = Meeting.Space(
                type = meetingRow[this.spaceType],
                location = meetingRow[this.location],
                images = imageRow.map { image -> image[MeetingImageTable.image] }
            ),
            description = meetingRow[this.description],
            meetingSchedules = scheduleRow.map { schedule ->
                MeetingSchedule(
                    date = schedule[MeetingScheduleTable.date],
                    meetingId = schedule[MeetingScheduleTable.meetingId].value,
                    time = schedule[MeetingScheduleTable.time],
                    maxMember = schedule[MeetingScheduleTable.maxMember],
                )
            },
            categories = categoryRow.map { category ->
                MeetingCategory(
                    category = category[MeetingCategoryTable.categoryName],
                    meetingId = category[MeetingCategoryTable.meetingId].value
                )
            },
            createMemberId = meetingRow[this.createMemberId].value,
            createdAt = meetingRow[this.createdAt],
            updatedAt = meetingRow[this.updatedAt]
        )
    }
}
