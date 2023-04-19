package core.member.service

import core.member.exception.AlreadyExistsException
import core.member.model.Email
import core.member.spi.QueryMemberPort

interface CheckMemberService {
    suspend fun checkNotExistsEmail(email: Email)
    suspend fun checkNotExistsPhoneNumber(phoneNumber: String)
}

class CheckMemberServiceImpl(private val queryMemberPort: QueryMemberPort) : CheckMemberService {
    override suspend fun checkNotExistsEmail(email: Email) {
        if (queryMemberPort.existsMemberByEmail(email.value)) {
            throw AlreadyExistsException(email.value)
        }
    }

    override suspend fun checkNotExistsPhoneNumber(phoneNumber: String) {
        if (queryMemberPort.existsMemberByPhoneNumber(phoneNumber)) {
            throw AlreadyExistsException(phoneNumber)
        }
    }
}
