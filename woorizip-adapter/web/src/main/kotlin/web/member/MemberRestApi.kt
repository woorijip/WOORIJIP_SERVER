package web.member

import core.member.usecase.SignIn
import core.member.usecase.SignUp
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import web.Api

class MemberRestApi(
    private val signUp: SignUp,
    private val signIn: SignIn
) : Api({
    route("/members") {
        post {
            val input = call.receive<SignUp.Input>()
            call.respond(
                message = signUp(input),
                status = HttpStatusCode.Created
            )
        }
    }

    route("/members/token") {
        post {
            val input = call.receive<SignIn.Input>()
            call.respond(
                message = signIn(input),
                status = HttpStatusCode.OK
            )
        }
    }
})
