package com.shubhamdani.todonotes

import com.shubhamdani.todonotes.data.TodoNotesRepository
import com.shubhamdani.todonotes.data.getTodoListener
import com.shubhamdani.todonotes.data.saveTodoListener
import com.shubhamdani.todonotes.data.services.NetWorkService
import com.shubhamdani.todonotes.model.TodoModel
import com.shubhamdani.todonotes.viewmodel.TodoNotesViewModel
import org.junit.Assert
import org.mockito.Mockito.*
import org.spekframework.spek2.Spek

object DemoTest : Spek({

    val todoVm by memoized {
        TodoNotesViewModel()
    }

    val network = mock(NetWorkService::class.java)

    val mRepo= mock(TodoNotesRepository::class.java)

    beforeEachTest {
        print("beforeEachTest")
    }

    test("Live data should not be null") {
        Assert.assertNotNull(todoVm.getTodoLiveData())
    }

    test("get Data from storage") {
        todoVm.getTodoDataFromStorage()
        verify(mRepo).getAllTodoNotes(mock(getTodoListener::class.java))
    }
})