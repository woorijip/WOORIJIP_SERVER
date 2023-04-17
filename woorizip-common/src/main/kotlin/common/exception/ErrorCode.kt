package common.exception

interface ErrorCode {
    val sequence: Int
    val message: String

    fun getCode(prefix: String, sequence: Int): String {
        val numberCode = "%03d".format(sequence)
        return "$prefix-$numberCode"
    }

    fun option(message: String, vararg args: Any): String = String.format(message, *args)
}
