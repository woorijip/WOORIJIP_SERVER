package core.member.service

import core.member.createMember
import core.member.exception.MemberNotFoundException
import core.member.model.Email
import core.member.spi.QueryMemberPort
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

internal class QueryMemberServiceTest : DescribeSpec({
    val queryMemberPort: QueryMemberPort = mockk()

    val queryMemberService = QueryMemberServiceImpl(queryMemberPort)

    describe("getMemberById를 호출했을 때") {
        val memberId = 1
        val member = createMember(id = memberId)

        context("값이 존재한다면") {
            coEvery { queryMemberPort.getMemberById(memberId) } returns member

            it("Member 객체를 반환한다.") {
                val result = queryMemberService.getMemberById(memberId)

                result shouldBe member
            }
        }

        context("값이 존재하지 않으면") {
            coEvery { queryMemberPort.getMemberById(memberId) } returns null

            it("MemberNotFoundException 예외를 반환한다.") {
                shouldThrow<MemberNotFoundException> {
                    queryMemberService.getMemberById(memberId)
                }
            }
        }
    }

    describe("getMemberByEmail를 호출했을 때") {
        val email = Email("test@test.com")
        val member = createMember(email = email)

        context("값이 존재한다면") {
            coEvery { queryMemberPort.getMemberByEmail(email.value) } returns member

            it("Member 객체를 반환한다.") {
                val result = queryMemberService.getMemberByEmail(email)

                result shouldBe member
            }
        }

        context("값이 존재 하지 않으면") {
            coEvery { queryMemberPort.getMemberByEmail(email.value) } returns null

            it("MemberNotFoundException 예외를 반환한다.") {
                shouldThrow<MemberNotFoundException> {
                    queryMemberService.getMemberByEmail(email)
                }
            }
        }
    }
})
