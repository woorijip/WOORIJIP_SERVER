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

        context("해당 이메일을 가지고 있는 회원이 존재하면") {
            coEvery { queryMemberPort.existsMemberByEmail(email.value) } returns true

            it("AlreadyExistsException 예외를 던진다.") {
                shouldThrow<AlreadyExistsException> {
                    checkMemberService.checkNotExistsEmail(email)
                }
            }
        }

        context("해당 이메일을 가지고 있는 회원이 존재하지 않으면") {
            coEvery { queryMemberPort.existsMemberByEmail(email.value) } returns false

            it("예외를 던지지 않는다.") {
                shouldNotThrowAny {
                    checkMemberService.checkNotExistsEmail(email)
                }
            }
        }
    }

    describe("checkNotExistsPhoneNumber를 호출했을 때") {
        val phoneNumber = "01011112222"

        context("해당 전화번호를 가지고 있는 회원이 존재하면") {
            coEvery { queryMemberPort.existsMemberByPhoneNumber(phoneNumber) } returns true

            it("AlreadyExistsException 예외를 던진다.") {
                shouldThrow<AlreadyExistsException> {
                    checkMemberService.checkNotExistsPhoneNumber(phoneNumber)
                }
            }
        }

        context("해당 전화번호를 가지고 있는 회원이 존재하지 않으면") {
            coEvery { queryMemberPort.existsMemberByPhoneNumber(phoneNumber) } returns false

            it("예외를 던지지 않는다.") {
                shouldNotThrowAny {
                    checkMemberService.checkNotExistsPhoneNumber(phoneNumber)
                }
            }
        }
    }
})
