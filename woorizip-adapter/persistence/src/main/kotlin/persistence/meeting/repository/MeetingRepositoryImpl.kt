package persistence.meeting.repository

import core.meeting.model.Meeting
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import persistence.meeting.model.MeetingCategoryTable
import persistence.meeting.model.MeetingImageTable
import persistence.meeting.model.MeetingScheduleTable
import persistence.meeting.model.MeetingTable
import persistence.meeting.model.toDomain

class MeetingRepositoryImpl : MeetingRepository {
    override suspend fun findMeetingBy(where: () -> Op<Boolean>): Meeting? { // TODO - N+1 문제 해결하기
        val meeting = MeetingTable
//            .leftJoin(MeetingImageTable, { MeetingTable.id }, { meetingId })
//            .leftJoin(MeetingCategoryTable, { MeetingTable.id }, { meetingId })
//            .leftJoin(MeetingScheduleTable, { MeetingTable.id }, { meetingId })
            .slice(
                MeetingTable.fields
//                    SubQueryExpression<MeetingImageTable>(
//                        MeetingImageTable
//                            .select { MeetingImageTable.meetingId eq MeetingTable.id }
//                            .groupBy(MeetingImageTable.meetingId)
//                            .alias("images")
//                    ) +
//                    SubQueryExpression<MeetingCategoryTable>(
//                        MeetingCategoryTable
//                            .select { MeetingCategoryTable.meetingId eq MeetingTable.id }
//                            .groupBy(MeetingCategoryTable.meetingId)
//                            .alias("categories")
//                    ) +
//                    SubQueryExpression<MeetingScheduleTable>(
//                        MeetingScheduleTable
//                            .select { MeetingScheduleTable.meetingId eq MeetingTable.id }
//                            .groupBy(MeetingScheduleTable.meetingId)
//                            .alias("schedules")
//                    )
            )
            .select(where())
            .singleOrNull()

        return meeting.let {
            MeetingTable.toDomain(
                meetingRow = it,
                imageRow = MeetingImageTable.select { MeetingImageTable.meetingId eq MeetingTable.id }
                    .map { i -> i },
                scheduleRow = MeetingScheduleTable.select { MeetingScheduleTable.meetingId eq MeetingTable.id }
                    .map { s -> s },
                categoryRow = MeetingCategoryTable.select { MeetingCategoryTable.meetingId eq MeetingTable.id }
                    .map { c -> c }
            )
        }
    }

//    class SubQueryExpression<T>(private val aliasQuery: QueryAlias) : Expression<T>() {
//        override fun toQueryBuilder(queryBuilder: QueryBuilder) {
//            aliasQuery.describe(TransactionManager.current(), queryBuilder)
//        }
//    }

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
            this[MeetingImageTable.meetingId] = meetingResult[MeetingTable.id]
            this[MeetingImageTable.image] = image
        }

        val scheduleResult = MeetingScheduleTable.batchInsert(meeting.meetingSchedules) { schedule ->
            this[MeetingScheduleTable.meetingId] = meetingResult[MeetingTable.id]
            this[MeetingScheduleTable.date] = schedule.date
            this[MeetingScheduleTable.time] = schedule.time
            this[MeetingScheduleTable.maxMember] = schedule.maxMember
        }

        val categoryResult = MeetingCategoryTable.batchInsert(meeting.categories) { category ->
            this[MeetingCategoryTable.meetingId] = meetingResult[MeetingTable.id]
            this[MeetingCategoryTable.categoryName] = category
        }

        return MeetingTable.toDomain(
            meetingRow = meetingResult,
            imageRow = imageResult,
            scheduleRow = scheduleResult,
            categoryRow = categoryResult
        )!!
    }
}
