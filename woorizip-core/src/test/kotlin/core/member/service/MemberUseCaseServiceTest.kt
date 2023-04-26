package core.member.service

import core.member.createMember
import core.member.exception.AlreadyExistsException
import core.member.model.Email
import core.member.model.Password
import core.member.spi.CommandMemberPort
import core.member.spi.QueryMemberPort
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class MemberUseCaseServiceTest : DescribeSpec({
    val queryMemberPort: QueryMemberPort = mockk()
    val commandMemberPort: CommandMemberPort = mockk()

    val memberUseCaseService = MemberUseCaseServiceImpl(queryMemberPort, commandMemberPort)

    describe("signUp을 호출할 때") {
        val email = Email("hhhh@naver.com")
        val phoneNumber = "01012345678"
        val password = Password("passwordpassword")
        val member = createMember(email = email, phoneNumber = phoneNumber, password = password)

        context("이미 존재하는 이메일이 입력되었을 때") {
            coEvery { queryMemberPort.existsMemberByEmail(email.value) } returns true

            it("AlreadyExistsException 예외를 반환한다.") {
                shouldThrow<AlreadyExistsException> {
                    memberUseCaseService.signUp(member)
                }
            }
        }

        context("이미 존재하는 전화번호가 입력되었을 때") {
            coEvery { queryMemberPort.existsMemberByEmail(email.value) } returns false
            coEvery { queryMemberPort.existsMemberByPhoneNumber(phoneNumber) } returns true

            it("AlreadyExistsException 예외를 반환한다.") {
                shouldThrow<AlreadyExistsException> {
                    memberUseCaseService.signUp(member)
                }
            }
        }

        context("중복된 회원이 존재하지 않으면") {
            val savedMember = member.copy(password = password.encode())

            coEvery { queryMemberPort.existsMemberByEmail(email.value) } returns false
            coEvery { queryMemberPort.existsMemberByPhoneNumber(phoneNumber) } returns false
            coEvery { commandMemberPort.saveMember(any()) } returns savedMember

            it("저장된 Member 객체를 반환한다.") {
                val result = memberUseCaseService.signUp(member)

                result shouldBe savedMember
            }
        }
    }

    describe("signIn을 호출했을 때") {
        val member = createMember(password = Password("passwordpassword").encode())
        val rawPassword = Password("passwordpassword")

        it("비밀번호를 비교한다.") {
            shouldNotThrowAny {
                memberUseCaseService.signIn(member, rawPassword)
            }
        }
    }
})
