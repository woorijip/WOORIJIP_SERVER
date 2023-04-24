package persistence.meeting.repository

import core.meeting.model.Category
import core.meeting.model.Meeting
import core.meeting.model.MeetingSchedule
import org.jetbrains.exposed.sql.Op
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
        TODO("Not yet implemented")
    }

    override suspend fun insertMeetingImage(meetingId: Long, imageUrls: List<String>): Meeting {
        TODO("Not yet implemented")
    }

    override suspend fun insertMeetingSchedule(meetingId: Long, schedules: List<MeetingSchedule>): Meeting {
        TODO("Not yet implemented")
    }

    override suspend fun insertMeetingCategory(meetingId: Long, categories: List<Category>): Meeting {
        TODO("Not yet implemented")
    }
}
