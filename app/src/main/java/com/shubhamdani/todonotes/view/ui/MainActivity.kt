package com.shubhamdani.todonotes.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.shubhamdani.todonotes.R
import com.shubhamdani.todonotes.model.TodoModel
import com.shubhamdani.todonotes.viewmodel.TodoNotesViewModel

class MainActivity : AppCompatActivity() {

    lateinit var mTodoNotesViewModel: TodoNotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mTodoNotesViewModel = ViewModelProviders.of(this).get(TodoNotesViewModel::class.java)

        mTodoNotesViewModel.getTodoDataFromStorage()
    }

}
