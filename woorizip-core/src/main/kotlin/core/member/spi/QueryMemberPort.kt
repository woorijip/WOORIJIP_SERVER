package core.member.spi

import core.member.model.Member

interface QueryMemberPort {
    suspend fun existsMemberByEmail(email: String): Boolean
    suspend fun existsMemberByPhoneNumber(phoneNumber: String): Boolean
    suspend fun getMemberByEmail(email: String): Member?
    suspend fun getMemberById(id: Int): Member?
}
