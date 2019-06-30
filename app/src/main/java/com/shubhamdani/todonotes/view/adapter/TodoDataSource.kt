package com.shubhamdani.todonotes.view.adapter

import androidx.annotation.NonNull
import androidx.paging.PageKeyedDataSource
import com.shubhamdani.todonotes.data.TodoNotesRepository
import com.shubhamdani.todonotes.data.getTodoListener
import com.shubhamdani.todonotes.model.TodoModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedDataSource(val repo: TodoNotesRepository) : PageKeyedDataSource<Long, TodoModel>() {
    private var nextKey: Long? = 1

    override fun loadInitial(
        @NonNull params: LoadInitialParams<Long>,
        @NonNull callback: LoadInitialCallback<Long, TodoModel>
    ) {

        repo.getAllTodoNotes(object : getTodoListener {
            override fun onGetTodoFail() {

            }

            override fun onGetTodoSuccess(list: ArrayList<TodoModel>) {
                nextKey = 2L
                callback.onResult(list, 1, nextKey)
            }
        })
    }


    override fun loadBefore(
        @NonNull params: LoadParams<Long>,
        @NonNull callback: LoadCallback<Long, TodoModel>
    ) {

    }


    /*
     * Step 3: This method it is responsible for the subsequent call to load the data page wise.
     * This method is executed in the background thread
     * We are fetching the next page data from the api
     * and passing it via the callback method to the UI.
     * The "params.key" variable will have the updated value.
     */
    override fun loadAfter(
        @NonNull params: LoadParams<Long>,
        @NonNull callback: LoadCallback<Long, TodoModel>
    ) {

        repo.getAllTodoNotes(object : getTodoListener {
            override fun onGetTodoFail() {

            }

            override fun onGetTodoSuccess(list: ArrayList<TodoModel>) {
                callback.onResult(list, nextKey)
            }
        })
    }

    companion object {

        private val TAG = FeedDataSource::class.java.simpleName
    }
}