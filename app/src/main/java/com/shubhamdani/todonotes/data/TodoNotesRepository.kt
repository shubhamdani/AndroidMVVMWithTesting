package com.shubhamdani.todonotes.data

import android.util.Log
import com.shubhamdani.todonotes.data.services.NetWorkService
import com.shubhamdani.todonotes.model.AllTodoResponceModel
import com.shubhamdani.todonotes.model.SavedTodoResponseModel
import com.shubhamdani.todonotes.model.TodoModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodoNotesRepository(private val service: NetWorkService) {

    fun getAllTodoNotes(listener: getTodoListener) {

        service.getAllTodoNotes().enqueue(object : Callback<AllTodoResponceModel> {
            override fun onFailure(call: Call<AllTodoResponceModel>?, t: Throwable?) {
                Log.d("onFailure", t.toString())
                listener.onGetTodoFail()
            }

            override fun onResponse(call: Call<AllTodoResponceModel>?, response: Response<AllTodoResponceModel>?) {
                Log.d("onResponse", response?.body()?.responseModel.toString())
                listener.onGetTodoSuccess(response?.body()?.responseModel as ArrayList)
            }
        })
    }

    fun addTodoNotes(todoModel: TodoModel, listener: saveTodoListener) {
        service.addTodoNotes(todoModel).enqueue(object : Callback<SavedTodoResponseModel> {
            override fun onFailure(call: Call<SavedTodoResponseModel>?, t: Throwable?) {
                Log.d("onFailure", t.toString())
                listener.onSaveTodoFail()
            }

            override fun onResponse(call: Call<SavedTodoResponseModel>?, response: Response<SavedTodoResponseModel>?) {
                Log.d("onResponse", response?.body()?.responseModel.toString())
                listener.onSaveTodoSuccess(todoModel)
            }

        })
    }
}


interface getTodoListener {
    fun onGetTodoFail()
    fun onGetTodoSuccess(list: ArrayList<TodoModel>)
}

interface saveTodoListener {
    fun onSaveTodoFail()
    fun onSaveTodoSuccess(todoModel: TodoModel)
}