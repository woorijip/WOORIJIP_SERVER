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
    val spaceType = varchar("space_type", length = 255)
    val description = varchar("description", length = 255)
    val createMemberId = reference("create_member_id", MemberTable.id)
    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
    val updatedAt = datetime("updated_at").clientDefault { LocalDateTime.now() }
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

internal fun MeetingTable.toDomain(row: ResultRow?): Meeting? {
    return row?.let {
        Meeting(
            id = row[this.id].value,
            name = row[this.name],
            introduction = row[this.introduction],
            thumbnail = row[this.thumbnailImage],
            space = Meeting.Space(
                type = Meeting.Space.SpaceType.valueOf(row[this.spaceType]),
                location = row[this.location],
                images = row[MeetingImageTable.image].split(",")
            ),
            description = row[this.description],
            meetingSchedules = listOf(
                MeetingSchedule(
                    date = row[MeetingScheduleTable.date],
                    meetingId = row[MeetingScheduleTable.meetingId].value,
                    time = row[MeetingScheduleTable.time],
                    maxMember = row[MeetingScheduleTable.maxMember],
                )
            ),
            categories = listOf(
                MeetingCategory(
                    category = row[MeetingCategoryTable.categoryName],
                    meetingId = row[MeetingCategoryTable.meetingId].value
                )
            ),
            createMemberId = row[this.createMemberId].value,
            createdAt = row[this.createdAt],
            updatedAt = row[this.updatedAt]
        )
    }
}
