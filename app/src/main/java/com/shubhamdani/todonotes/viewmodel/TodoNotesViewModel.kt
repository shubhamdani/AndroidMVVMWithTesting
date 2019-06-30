package com.shubhamdani.todonotes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.shubhamdani.todonotes.TodoApplication
import com.shubhamdani.todonotes.data.RepoContract
import com.shubhamdani.todonotes.data.TodoNotesRepository
import com.shubhamdani.todonotes.data.saveTodoListener
import com.shubhamdani.todonotes.model.TodoModel
import com.shubhamdani.todonotes.view.adapter.TodoDataFactory

open class TodoNotesViewModel(private val mRepo: RepoContract = TodoNotesRepository(TodoApplication.NETWORK_SERVICE)) :
    ViewModel(), saveTodoListener {

    private lateinit var mLiveTodoData :  LiveData<PagedList<TodoModel>>
    private lateinit var listener: AddTodoListener

    private var dataSource: MutableLiveData<PageKeyedDataSource<Long, TodoModel>>

    private var feedDataFactory: TodoDataFactory

    init {
        feedDataFactory = TodoDataFactory()
        dataSource = feedDataFactory.mutableLiveData

        val pagelistConfig = PagedList.Config.Builder().setEnablePlaceholders(false).setInitialLoadSizeHint(5).setPageSize(5).build()
        mLiveTodoData = LivePagedListBuilder(feedDataFactory, pagelistConfig).build()
    }

    fun addData(todoModel: TodoModel, listener: AddTodoListener) {
        this.listener = listener
        mRepo.addTodoNotes(todoModel, this)
    }

    override fun onSaveTodoFail() {

    }

    override fun onSaveTodoSuccess(todoModel: TodoModel) {
        feedDataFactory.invalideData()
        listener.onTodoAdded()
    }

    fun getTodoLiveData() = mLiveTodoData

}

interface AddTodoListener {
    fun onTodoAdded()
}