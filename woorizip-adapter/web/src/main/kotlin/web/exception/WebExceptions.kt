package web.exception

import common.exception.BaseException

class InvalidQueryParamException(
    override val code: String = WebErrorCode.INVALID_QUERY_PARAM.code,
    override val message: String = WebErrorCode.INVALID_QUERY_PARAM.message
) : BaseException.BadRequestException(code, message)

class InvalidPathParamException(
    override val code: String = WebErrorCode.INVALID_PATH_PARAM.code,
    override val message: String = WebErrorCode.INVALID_PATH_PARAM.message
) : BaseException.BadRequestException(code, message)
