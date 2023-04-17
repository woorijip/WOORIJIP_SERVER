package common.exception

interface ErrorCode {
    val sequence: Int
    val message: String

    fun getCode(prefix: String): String {
        val numberCode = "%03d".format(this.sequence)
        return "$prefix-$numberCode"
    }

    fun option(vararg args: Any): String = String.format(this.message, *args)
}
