package com.shubhamdani.todonotes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shubhamdani.todonotes.TodoApplication
import com.shubhamdani.todonotes.data.TodoNotesRepository
import com.shubhamdani.todonotes.data.getTodoListener
import com.shubhamdani.todonotes.data.saveTodoListener
import com.shubhamdani.todonotes.model.TodoModel

class TodoNotesViewModel : ViewModel(), getTodoListener, saveTodoListener {

    private val mRepo = TodoNotesRepository(TodoApplication.NETWORK_SERVICE)
    private val mLiveTodoData = MutableLiveData<ArrayList<TodoModel>>()
    private lateinit var listener: AddTodoListener

    fun getTodoDataFromStorage() {
        mRepo.getAllTodoNotes(this)
    }

    fun addData(todoModel: TodoModel, listener: AddTodoListener) {
        this.listener = listener
        mRepo.addTodoNotes(todoModel, this)
    }

    override fun onSaveTodoFail() {

    }

    override fun onSaveTodoSuccess(todoModel: TodoModel) {
        mLiveTodoData.value?.add(todoModel)
        listener.onTodoAdded()
    }

    fun getTodoLiveData() = mLiveTodoData

    override fun onGetTodoFail() {

    }

    override fun onGetTodoSuccess(list: ArrayList<TodoModel>) {
        mLiveTodoData.value = list
    }
}

interface AddTodoListener {
    fun onTodoAdded()
}