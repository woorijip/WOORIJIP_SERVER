package common.exception

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val code: String,
    val message: String,
    val arguments: Collection<String>? = null
)
