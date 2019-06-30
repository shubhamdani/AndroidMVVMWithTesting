package com.shubhamdani.todonotes

/**
 * Fake Call for our network library used to observe results
 */
class FakeNetworkCall<T> {
    var result: FakeNetworkResult<T>? = null

    val listeners = mutableListOf<FakeNetworkListener<T>>()

    /**
     * Register a result listener to observe this callback.
     *
     * Errors will be passed to this callback as an instance of [FakeNetworkError] and successful
     * calls will be passed to this callback as an instance of [FakeNetworkSuccess].
     *
     * @param listener the callback to call when this request completes
     */
    fun addOnResultListener(listener: (FakeNetworkResult<T>) -> Unit) {
        trySendResult(listener)
        listeners += listener
    }

    /**
     * The library will call this when a result is available
     */
    fun onSuccess(data: T) {
        result = FakeNetworkSuccess(data)
        sendResultToAllListeners()
    }

    /**
     * The library will call this when an error happens
     */
    fun onError(throwable: Throwable) {
        result = FakeNetworkError(throwable)
        sendResultToAllListeners()
    }

    /**
     * Broadcast the current result (success or error) to all registered listeners.
     */
    private fun sendResultToAllListeners() = listeners.map { trySendResult(it) }

    /**
     * Send the current result to a specific listener.
     *
     * If no result is set (null), this method will do nothing.
     */
    private fun trySendResult(listener: FakeNetworkListener<T>) {
        val thisResult = result
        thisResult?.let {
            listener(thisResult)
        }
    }
}

/**
 * Network result class that represents both success and errors
 */
sealed class FakeNetworkResult<T>

/**
 * Passed to listener when the network request was successful
 *
 * @param data the result
 */
class FakeNetworkSuccess<T>(val data: T) : FakeNetworkResult<T>()

/**
 * Passed to listener when the network failed
 *
 * @param error the exception that caused this error
 */
class FakeNetworkError<T>(val error: Throwable) : FakeNetworkResult<T>()

/**
 * Listener "type" for observing a [FakeNetworkCall]
 */
typealias FakeNetworkListener<T> = (FakeNetworkResult<T>) -> Unit

/**
 * Throwable to use in fake network errors.
 */
class FakeNetworkException(message: String) : Throwable(message)
