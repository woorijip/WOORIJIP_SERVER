package core.member.service

import core.meeting.model.Category
import core.member.createInterestCategory
import core.member.createMember
import core.member.spi.CommandMemberPort
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class CommandMemberServiceTest : DescribeSpec({
    val commandMemberPort: CommandMemberPort = mockk()

    val commandMemberService = CommandMemberServiceImpl(commandMemberPort)

    describe("saveMember를 호출했을 때") {
        val member = createMember()

        coEvery { commandMemberPort.saveMember(any()) } returns member

        it("저장된 Member 객체를 반환한다.") {
            val result = commandMemberService.saveMember(member)

            member shouldBe result
        }
    }

    describe("saveInterestCategories를 호출했을 때") {
        val memberId = 1L
        val categories = listOf(Category.ALCOHOL, Category.HEALTH, Category.PET)
        val interestCategories = categories.map { createInterestCategory(memberId = memberId, category = it) }

        coEvery { commandMemberPort.saveAllInterestCategories(memberId, any()) } returns interestCategories

        it("저장된 InterestCategory 객체 배열을 반환한다.") {
            val result = commandMemberService.saveInterestCategories(memberId, categories)

            result shouldBe interestCategories
        }
    }
})
