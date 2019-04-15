package com.shubhamdani.todonotes.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.shubhamdani.todonotes.R
import com.shubhamdani.todonotes.model.TodoModel
import com.shubhamdani.todonotes.viewmodel.AddTodoListener
import com.shubhamdani.todonotes.viewmodel.TodoNotesViewModel
import kotlinx.android.synthetic.main.add_todo_fragment.*

class AddTodoFragment : Fragment(), View.OnClickListener, AddTodoListener {
    private lateinit var mTodoNotesViewModel: TodoNotesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_todo_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnAddTodo.setOnClickListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTodoNotesViewModel = ViewModelProviders.of(activity!!).get(TodoNotesViewModel::class.java)
    }


    override fun onClick(v: View?) {
        mTodoNotesViewModel.addData(TodoModel(heading = edtTitle.text.toString(), description = edtDescription.text.toString()), this)
    }

    override fun onTodoAdded() {
        NavHostFragment.findNavController(this).popBackStack()
    }
}
