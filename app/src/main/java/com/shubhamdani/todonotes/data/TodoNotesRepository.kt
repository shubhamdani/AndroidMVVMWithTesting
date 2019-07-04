package com.shubhamdani.todonotes.data

import android.util.Log
import com.shubhamdani.todonotes.data.services.NetWorkService
import com.shubhamdani.todonotes.model.AllTodoResponceModel
import com.shubhamdani.todonotes.model.SavedTodoResponseModel
import com.shubhamdani.todonotes.model.TodoModel
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext


open class TodoNotesRepository(
    private val service: NetWorkService?
) : CoroutineScope, RepoContract {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + SupervisorJob()

    val resultContext: CoroutineContext = Dispatchers.Main

    override fun getAllTodoNotes(listener: getTodoListener) {
        launch {
            val execute: Response<AllTodoResponceModel>?

            try {
                execute = service?.getAllTodoNotes()?.execute()

                withContext(resultContext) {
                    when (execute?.isSuccessful) {
                        true -> {
                            listener.onGetTodoSuccess(execute.body().responseModel as ArrayList<TodoModel>)
                        }
                        else -> {
                            listener.onGetTodoFail()
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d("API Exception", e.message)
                listener.onGetTodoFail()
            }
        }
    }

    override fun addTodoNotes(todoModel: TodoModel, listener: saveTodoListener) {

        service?.addTodoNotes(todoModel)?.enqueue(object : Callback<SavedTodoResponseModel> {
            override fun onFailure(call: Call<SavedTodoResponseModel>?, t: Throwable?) {
                Log.d("onFailure", t.toString())
                listener.onSaveTodoFail()
            }

            override fun onResponse(
                call: Call<SavedTodoResponseModel>?,
                response: Response<SavedTodoResponseModel>?
            ) {
                Log.d("onResponse", response?.body()?.responseModel.toString())
                listener.onSaveTodoSuccess(todoModel)
            }

        })
    }
}

interface RepoContract {
    fun getAllTodoNotes(listener: getTodoListener)
    fun addTodoNotes(todoModel: TodoModel, listener: saveTodoListener)
}

interface getTodoListener {
    fun onGetTodoFail()
    fun onGetTodoSuccess(list: ArrayList<TodoModel>)
}

interface saveTodoListener {
    fun onSaveTodoFail()
    fun onSaveTodoSuccess(todoModel: TodoModel)
}