package persistence.meeting.repository

import core.meeting.model.Meeting
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.batchInsert
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
    override suspend fun findAllMeetings(where: () -> Op<Boolean>): List<Meeting> {
        val meetings = MeetingTable.select(where()).toList()

        val meetingIds = mutableListOf<Long>()
        meetings.forEach {
            meetingIds.add(it[MeetingTable.id].value)
        }

        val images = MeetingImageTable.select { MeetingImageTable.meetingId inList meetingIds }.toList()
        val schedules = MeetingScheduleTable.select { MeetingScheduleTable.meetingId inList meetingIds }.toList()
        val categories = MeetingCategoryTable.select { MeetingCategoryTable.meetingId inList meetingIds }.toList()

        return meetings.map { meeting ->
            val meetingId = meeting[MeetingTable.id]
            MeetingTable.toDomainNotNull(
                meetingRow = meeting,
                imageRow = images.filter { it[MeetingImageTable.meetingId] == meetingId },
                scheduleRow = schedules.filter { it[MeetingScheduleTable.meetingId] == meetingId },
                categoryRow = categories.filter { it[MeetingCategoryTable.meetingId] == meetingId }
            )
        }
    }

    override suspend fun findMeetingById(meetingId: Long): Meeting? {
        return MeetingTable.toDomain(
            meetingRow = MeetingTable.select { MeetingTable.id eq meetingId }.single(),
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
}
