package web.meeting

import core.meeting.model.Category
import core.meeting.model.WeekType
import core.meeting.usecase.CreateMeeting
import core.meeting.usecase.GetMeetingDetails
import core.meeting.usecase.GetMeetings
import core.meeting.usecase.RemoveMeeting
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import web.Api
import web.context.MemberContextHolder
import web.exception.InvalidPathParamException

class MeetingRestApi(
    private val createMeeting: CreateMeeting,
    private val getMeetings: GetMeetings,
    private val getMeetingDetails: GetMeetingDetails,
    private val removeMeeting: RemoveMeeting
) : Api({
    route("/meetings") {
        authenticate {
            post {
                val input = call.receive<CreateMeeting.Input>()

                val currentMemberId = MemberContextHolder.getContext().getMemberId()

                call.respond(
                    message = createMeeting(input, currentMemberId),
                    status = HttpStatusCode.Created
                )
            }

            get {
                val categories = call.request.queryParameters.getAll("categories")
                val weekType = call.request.queryParameters["weekType"]
                val name = call.request.queryParameters["name"]

                val input = GetMeetings.Input(
                    categories = categories?.map { Category.valueOf(it) },
                    weekType = weekType?.let { WeekType.valueOf(it) },
                    name = name
                )

                call.respond(
                    message = getMeetings(input),
                    status = HttpStatusCode.OK
                )
            }

            get("/{meetingId}") {
                val meetingId = call.parameters["meetingId"]?.toLong() ?: throw InvalidPathParamException()

                call.respond(
                    message = getMeetingDetails(meetingId),
                    status = HttpStatusCode.OK
                )
            }

            delete("/{meetingId}") {
                val meetingId = call.parameters["meetingId"]?.toLong() ?: throw InvalidPathParamException()

                call.respond(
                    message = removeMeeting(meetingId),
                    status = HttpStatusCode.NoContent
                )
            }
        }
    }
})
