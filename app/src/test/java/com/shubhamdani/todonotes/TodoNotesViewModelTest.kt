package com.shubhamdani.todonotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.verify
import com.shubhamdani.todonotes.data.TodoNotesRepository
import com.shubhamdani.todonotes.data.getTodoListener
import com.shubhamdani.todonotes.data.services.NetWorkService
import com.shubhamdani.todonotes.model.TodoModel
import com.shubhamdani.todonotes.viewmodel.TodoNotesViewModel
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class TodoNotesViewModelTest : AndroidTest() {


    lateinit var todoNotesViewModel: TodoNotesViewModel

    @Mock
    lateinit var service: NetWorkService

    @Mock
    lateinit var repo: TodoNotesRepository

    @Mock
    lateinit var listener: getTodoListener

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        todoNotesViewModel = TodoNotesViewModel(repo)
    }

    @Test
    fun test1() {
        todoNotesViewModel.getTodoDataFromStorage()
        verify(repo).getAllTodoNotes(any())
    }

    @Test
    fun test2() {
        todoNotesViewModel.getTodoLiveData().captureValues {
            Mockito.`when`(repo.getAllTodoNotes(listener)).thenAnswer {
                todoNotesViewModel.onGetTodoSuccess(arrayListOf<TodoModel>(TodoModel(1, "", "")))
                return@thenAnswer null
            }

            doAnswer {
                listener.onGetTodoSuccess(arrayListOf<TodoModel>(TodoModel(1, "", "")))
                return@doAnswer null
            }.`when`(repo).getAllTodoNotes(listener)


            todoNotesViewModel.getTodoDataFromStorage()
            runBlocking {
                assertSendsValues(
                    2_0000,
                    arrayListOf<TodoModel>(TodoModel(1, "", ""))
                )
            }
        }
    }
}


/**
 * Extension function to capture all values that are emitted to a LiveData<T> during the execution of
 * `captureBlock`.
 *
 * @param captureBlock a lambda that will
 */
inline fun <T> LiveData<T>.captureValues(block: LiveDataValueCapture<T>.() -> Unit) {
    val capture = LiveDataValueCapture<T>()
    val observer = Observer<T> {
        capture.addValue(it)
    }
    observeForever(observer)
    capture.block()
//    removeObserver(observer)
//    rem
}

/**
 * Represents a list of capture values from a LiveData.
 *
 * This class is not threadsafe and must be used from the main thread.
 */
class LiveDataValueCapture<T> {

    private val _values = mutableListOf<T?>()
    val values: List<T?>
        get() = _values

    val channel = Channel<T?>(Channel.UNLIMITED)

    fun addValue(value: T?) {
        _values += value
        channel.offer(value)
    }

    suspend fun assertSendsValues(timeout: Long, vararg expected: T?) {
        val expectedList = expected.asList()
        if (values == expectedList) {
            return
        }
        try {
            withTimeout(timeout) {
                for (value in channel) {
                    if (values == expectedList) {
                        return@withTimeout
                    }
                }
            }
        } catch (ex: TimeoutCancellationException) {
            Assert.assertEquals(values, expectedList)
        }
    }
}