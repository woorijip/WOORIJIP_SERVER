package core.member.service

import core.member.exception.AlreadyExistsException
import core.member.model.Email
import core.member.spi.QueryMemberPort
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.coEvery
import io.mockk.mockk

class CheckMemberServiceTest : DescribeSpec({
    val queryMemberPort: QueryMemberPort = mockk()

    val checkMemberService = CheckMemberServiceImpl(queryMemberPort)

    describe("checkNotExistsEmail을 호출했을 때") {
        val email = Email("kim@kim.com")

        context("값이 존재한다면") {
            coEvery { queryMemberPort.existsMemberByEmail(email.value) } returns true

            it("AlreadyExistsException 예외를 반환한다.") {
                shouldThrow<AlreadyExistsException> {
                    checkMemberService.checkNotExistsEmail(email)
                }
            }
        }

        context("값이 존재하지 않으면") {
            coEvery { queryMemberPort.existsMemberByEmail(email.value) } returns false

            it("예외를 반환하지 않는다.") {
                shouldNotThrowAny {
                    checkMemberService.checkNotExistsEmail(email)
                }
            }
        }
    }

    describe("checkNotExistsPhoneNumber를 호출했을 때") {
        val phoneNumber = "01011112222"

        context("값이 존재한다면") {
            coEvery { queryMemberPort.existsMemberByPhoneNumber(phoneNumber) } returns true

            it("AlreadyExistsException 예외를 반환한다.") {
                shouldThrow<AlreadyExistsException> {
                    checkMemberService.checkNotExistsPhoneNumber(phoneNumber)
                }
            }
        }

        context("값이 존재하지 않으면") {
            coEvery { queryMemberPort.existsMemberByPhoneNumber(phoneNumber) } returns false

            it("예외를 반환하지 않는다.") {
                shouldNotThrowAny {
                    checkMemberService.checkNotExistsPhoneNumber(phoneNumber)
                }
            }
        }
    }
})
