package core.member.service

import core.meeting.model.Category
import core.member.createInterestCategory
import core.member.createMember
import core.member.exception.AlreadyExistsException
import core.member.model.Email
import core.member.model.Password
import core.member.spi.CommandMemberPort
import core.member.spi.QueryMemberPort
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class CommandMemberServiceTest : DescribeSpec({
    val queryMemberPort: QueryMemberPort = mockk()
    val commandMemberPort: CommandMemberPort = mockk()

    val commandMemberService = CommandMemberServiceImpl(queryMemberPort, commandMemberPort)

    describe("createMember를 호출할 때") {
        val email = Email("hhhh@naver.com")
        val phoneNumber = "01012345678"
        val password = Password("passwordpassword")
        val member = createMember(email = email, phoneNumber = phoneNumber, password = password)

        context("해당 이메일을 가지고 있는 회원이 존재하면") {
            coEvery { queryMemberPort.existsMemberByEmail(email.value) } returns true

            it("AlreadyExistsException 예외를 던진다.") {
                shouldThrow<AlreadyExistsException> {
                    commandMemberService.createMember(member)
                }
            }
        }

        context("해당 전화번호를 가지고 있는 회원이 존재하면") {
            coEvery { queryMemberPort.existsMemberByEmail(email.value) } returns false
            coEvery { queryMemberPort.existsMemberByPhoneNumber(phoneNumber) } returns true

            it("AlreadyExistsException 예외를 던진다.") {
                shouldThrow<AlreadyExistsException> {
                    commandMemberService.createMember(member)
                }
            }
        }

        context("중복된 회원이 존재하지 않으면") {
            val encodedMember = member.copy(password = password.encode())

            coEvery { queryMemberPort.existsMemberByEmail(email.value) } returns false
            coEvery { queryMemberPort.existsMemberByPhoneNumber(phoneNumber) } returns false
            coEvery { commandMemberPort.createMember(any()) } returns encodedMember

            it("저장된 Member 객체를 반환한다.") {
                val result = commandMemberService.createMember(member)

                result shouldBe encodedMember
            }
        }
    }

    describe("createInterestCategories를 호출했을 때") {
        val memberId = 1L
        val categories = listOf(Category.ALCOHOL, Category.HEALTH, Category.PET)
        val interestCategories = categories.map { createInterestCategory(memberId = memberId, category = it) }

        coEvery { commandMemberPort.createAllInterestCategories(memberId, any()) } returns interestCategories

        it("저장된 InterestCategory 객체 배열을 반환한다.") {
            val result = commandMemberService.createInterestCategories(memberId, categories)

            result shouldBe interestCategories
        }
    }
})
