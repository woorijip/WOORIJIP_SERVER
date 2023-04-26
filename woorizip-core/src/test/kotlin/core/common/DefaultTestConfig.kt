package core.common

import io.kotest.core.listeners.TestListener
import io.kotest.core.spec.AutoScan
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.mockk.clearAllMocks

@AutoScan
object DefaultTestConfig : TestListener {
    override suspend fun afterEach(testCase: TestCase, result: TestResult) = clearAllMocks()
}
