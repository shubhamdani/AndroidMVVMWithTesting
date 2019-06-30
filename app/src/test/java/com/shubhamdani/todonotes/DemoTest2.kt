package com.shubhamdani.todonotes

import com.nhaarman.mockito_kotlin.verify
import com.shubhamdani.todonotes.data.RepoContract
import com.shubhamdani.todonotes.data.TodoNotesRepository
import com.shubhamdani.todonotes.data.getTodoListener
import com.shubhamdani.todonotes.data.saveTodoListener
import com.shubhamdani.todonotes.data.services.NetWorkService
import com.shubhamdani.todonotes.model.AllTodoResponceModel
import com.shubhamdani.todonotes.model.TodoModel
import com.shubhamdani.todonotes.viewmodel.TodoNotesViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import retrofit2.Call
import retrofit2.Response

class DemoTest2 : AndroidTest() {

    lateinit var todoNotesRepository: TodoNotesRepository

    @Mock
    lateinit var service: NetWorkService

    @Mock
    lateinit var call: Call<AllTodoResponceModel>
    @Mock
    lateinit var listener: getTodoListener

    var responseBody = AllTodoResponceModel("", listOf(), 200)
    var response = Response.success(responseBody)

    val listOf = arrayListOf<TodoModel>()


    @Before
    fun setup() {
        todoNotesRepository = TodoNotesRepository(service)

//        listOf.add(TodoModel(1, "H", "D"))

       Mockito.`when`(service.getAllTodoNotes()).thenReturn(call)

//        doAnswer {
//            listener.onGetTodoSuccess(listOf)
//        }.`when`(call).execute()

//        Mockito.`when`(call.execute()).thenReturn(response)
    }


    @Test
    fun tesst() {

        val todoNotesViewModel = TodoNotesViewModel(todoNotesRepository)

        runBlocking {
            todoNotesViewModel.getTodoDataFromStorage()
        }
        verify(todoNotesViewModel).onGetTodoSuccess(listOf)
    }

}

class TestRepo(val list: ArrayList<TodoModel>) : RepoContract {
    override fun addTodoNotes(todoModel: TodoModel, listener: saveTodoListener) {
        listener.onSaveTodoSuccess(todoModel)
    }


    override fun getAllTodoNotes(listener: getTodoListener) {
        listener.onGetTodoSuccess(list)
    }
}



