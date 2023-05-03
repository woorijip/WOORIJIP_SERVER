package persistence.meeting.repository

import core.meeting.model.Category
import core.meeting.model.Meeting
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import persistence.common.toResultRow
import persistence.meeting.model.MeetingCategoryTable
import persistence.meeting.model.MeetingImageTable
import persistence.meeting.model.MeetingScheduleTable
import persistence.meeting.model.MeetingTable
import persistence.meeting.model.toDomain
import persistence.meeting.model.toDomainNotNull

class MeetingRepositoryImpl : MeetingRepository {
    override suspend fun findAllMeetings(
        categories: List<Category>,
        where: () -> Op<Boolean>
    ): List<Meeting> {
        val meetings = if (categories.isNotEmpty()) {
            MeetingTable
                .innerJoin(MeetingCategoryTable)
                .slice(MeetingTable.fields)
                .select(where())
                .andWhere {
                    MeetingCategoryTable.categoryName inList categories
                }
                .groupBy(MeetingTable.id)
                .toList()
        } else {
            MeetingTable.select(where()).toList()
        }

        val meetingIds = mutableListOf<Long>()
        meetings.forEach {
            meetingIds.add(it[MeetingTable.id].value)
        }

        val meetingImages = MeetingImageTable
            .select { MeetingImageTable.meetingId inList meetingIds }
            .groupBy { it[MeetingImageTable.meetingId].value }
        val meetingSchedules = MeetingScheduleTable
            .select { MeetingScheduleTable.meetingId inList meetingIds }
            .groupBy { it[MeetingScheduleTable.meetingId].value }
        val meetingCategories = MeetingCategoryTable
            .select { MeetingCategoryTable.meetingId inList meetingIds }
            .groupBy { it[MeetingCategoryTable.meetingId].value }

        return meetings.map { meeting ->
            val meetingId = meeting[MeetingTable.id].value
            MeetingTable.toDomainNotNull(
                meetingRow = meeting,
                imageRow = meetingImages[meetingId] ?: emptyList(),
                scheduleRow = meetingSchedules[meetingId] ?: emptyList(),
                categoryRow = meetingCategories[meetingId] ?: emptyList()
            )
        }
    }

    override suspend fun findMeetingById(meetingId: Long): Meeting? {
        return MeetingTable.toDomain(
            meetingRow = MeetingTable.select { MeetingTable.id eq meetingId }.singleOrNull(),
            imageRow = MeetingImageTable.select { MeetingImageTable.meetingId eq meetingId }.toResultRow(),
            scheduleRow = MeetingScheduleTable.select { MeetingScheduleTable.meetingId eq meetingId }.toResultRow(),
            categoryRow = MeetingCategoryTable.select { MeetingCategoryTable.meetingId eq meetingId }.toResultRow()
        )
    }

    override suspend fun findMeetingBy(where: () -> Op<Boolean>): Meeting? {
        return MeetingTable.toDomain(
            meetingRow = MeetingTable.select(where()).singleOrNull(),
            imageRow = MeetingImageTable.select(where()).toResultRow(),
            scheduleRow = MeetingScheduleTable.select(where()).toResultRow(),
            categoryRow = MeetingCategoryTable.select(where()).toResultRow()
        )
    }

    override suspend fun existsMeetingBy(where: () -> Op<Boolean>): Boolean {
        return MeetingTable
            .select(where())
            .empty().not()
    }

    override suspend fun insertMeeting(meeting: Meeting): Meeting {
        val meetingResult = MeetingTable.insert {
            it[id] = meeting.id
            it[name] = meeting.name
            it[introduction] = meeting.introduction
            it[thumbnailImage] = meeting.thumbnail
            it[location] = meeting.space.location
            it[spaceType] = meeting.space.type
            it[description] = meeting.description
            it[meetingCount] = meeting.meetingCount
            it[createMemberId] = meeting.createMemberId
            it[createdAt] = meeting.createdAt
            it[updatedAt] = meeting.updatedAt
        }.resultedValues!!.single()

        val imageResult = MeetingImageTable.batchInsert(meeting.space.images) { image ->
            this[MeetingImageTable.id] = image.id
            this[MeetingImageTable.meetingId] = meetingResult[MeetingTable.id]
            this[MeetingImageTable.image] = image.image
        }

        val scheduleResult = MeetingScheduleTable.batchInsert(meeting.meetingSchedules) { schedule ->
            this[MeetingScheduleTable.id] = schedule.id
            this[MeetingScheduleTable.meetingId] = meetingResult[MeetingTable.id]
            this[MeetingScheduleTable.date] = schedule.date
            this[MeetingScheduleTable.time] = schedule.time
            this[MeetingScheduleTable.maxMember] = schedule.maxMember
        }

        val categoryResult = MeetingCategoryTable.batchInsert(meeting.categories) { category ->
            this[MeetingCategoryTable.id] = category.id
            this[MeetingCategoryTable.meetingId] = meetingResult[MeetingTable.id]
            this[MeetingCategoryTable.categoryName] = category.category
        }

        return MeetingTable.toDomainNotNull(
            meetingRow = meetingResult,
            imageRow = imageResult,
            scheduleRow = scheduleResult,
            categoryRow = categoryResult
        )
    }

    override suspend fun deleteMeeting(meeting: Meeting) {
        MeetingImageTable.deleteWhere { meetingId eq meeting.id }
        MeetingScheduleTable.deleteWhere { meetingId eq meeting.id }
        MeetingCategoryTable.deleteWhere { meetingId eq meeting.id }
        MeetingTable.deleteWhere { MeetingTable.id eq meeting.id }
    }
}
