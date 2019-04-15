package com.shubhamdani.todonotes.data.services

import com.shubhamdani.todonotes.model.AllTodoResponceModel
import com.shubhamdani.todonotes.model.SavedTodoResponseModel
import com.shubhamdani.todonotes.model.TodoModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface NetWorkService {
    companion object {
        const val HTTPS_API_BASE_URL = "http://10.0.2.2:8080/todo/"
    }

    @GET("getAllTodo")
    fun getAllTodoNotes(): Call<AllTodoResponceModel>

    @POST("saveTodobyPost")
    fun addTodoNotes(@Body todoModel: TodoModel): Call<SavedTodoResponseModel>


}
