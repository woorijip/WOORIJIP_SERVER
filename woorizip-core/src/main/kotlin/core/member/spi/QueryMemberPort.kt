package core.member.spi

interface QueryMemberPort {
    suspend fun existsMemberByEmail(email: String): Boolean
    suspend fun existsMemberByPhoneNumber(phoneNumber: String): Boolean
}
