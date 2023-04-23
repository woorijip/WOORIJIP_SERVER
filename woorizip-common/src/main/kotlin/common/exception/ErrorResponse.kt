package common.exception

data class ErrorResponse(
    val code: String,
    val message: String,
    val arguments: Collection<String>? = null
)
