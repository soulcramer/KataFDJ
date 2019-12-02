package app.soulcramer.domain.interactor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.util.concurrent.TimeUnit

abstract class UseCase<in P, R>(
    protected val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(params: P, timeoutMs: Long = defaultTimeoutMs): R {
        return withContext(dispatcher) {
            withTimeout(timeoutMs) {
                doWork(params)
            }
        }
    }

    protected abstract suspend fun doWork(params: P): R

    companion object {
        private val defaultTimeoutMs = TimeUnit.MINUTES.toMillis(5)
    }
}

suspend operator fun <R> UseCase<Unit, R>.invoke(): R = this(Unit)