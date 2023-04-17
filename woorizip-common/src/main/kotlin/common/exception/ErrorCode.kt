package common.exception

interface ErrorCode {
    fun getCode(prefix: String, ordinal: Int): String {
        return when (val number = ordinal + 1) {
            in UNITS_MIN..UNITS_MAX -> {
                "$prefix-00$number"
            }

            in TENS_MIN..TENS_MAX -> {
                "$prefix-0$number"
            }

            else -> {
                "$prefix-$number"
            }
        }
    }

    private companion object {
        private const val UNITS_MIN = 1
        private const val UNITS_MAX = 9
        private const val TENS_MIN = 10
        private const val TENS_MAX = 99
    }
}
