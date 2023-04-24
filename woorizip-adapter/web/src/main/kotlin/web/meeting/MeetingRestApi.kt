package web.meeting

import core.context.MemberContextHolder
import core.meeting.usecase.CreateMeeting
import core.member.exception.MemberNotFoundException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import web.Api

class MeetingRestApi(
    private val createMeeting: CreateMeeting
) : Api({
    route("/meetings") {
        authenticate {
            post {
                val input = call.receive<CreateMeeting.Input>()

                val currentMemberId = MemberContextHolder.getMemberId() ?: throw MemberNotFoundException(
                    message = "Member not found in Context holder"
                )

                call.respond(
                    message = createMeeting(input, currentMemberId),
                    status = HttpStatusCode.Created
                )
            }
        }
    }
})
