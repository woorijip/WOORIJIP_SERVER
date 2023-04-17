package common.exception

interface ErrorCode {
    fun getCode(prefix: String, ordinal: Int): String {
        val numberCode = "%03d".format(ordinal)
        return "$prefix-$numberCode"
    }

    fun option(message: String, vararg args: Any): String = String.format(message, *args)
}
