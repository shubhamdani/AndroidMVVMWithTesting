package com.shubhamdani.todonotes.view.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.shubhamdani.todonotes.R
import com.shubhamdani.todonotes.model.TodoModel
import com.shubhamdani.todonotes.view.adapter.AllTodosAdapter
import com.shubhamdani.todonotes.viewmodel.TodoNotesViewModel
import kotlinx.android.synthetic.main.home_fragment.*

class HomeFragment : Fragment(), View.OnClickListener, Observer<List<TodoModel>> {

    private lateinit var mTodoNotesViewModel: TodoNotesViewModel


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTodoNotesViewModel = ViewModelProviders.of(activity!!).get(TodoNotesViewModel::class.java)
        mTodoNotesViewModel.getTodoLiveData().observe(this, this)


        rv_all_todo.adapter = AllTodosAdapter(mTodoNotesViewModel.getTodoLiveData().value)
        rv_all_todo.layoutManager = LinearLayoutManager(activity)
        fab_add_todo.setOnClickListener(this)
    }

    override fun onChanged(t: List<TodoModel>) {
        rv_all_todo.adapter = AllTodosAdapter(t)
        (rv_all_todo.adapter as AllTodosAdapter).notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        /**
         * Finds the host fragment is NavHost or not: NavHostFragment.findNavController(this)
         */
        val actionHomeFragmentToAddTodoFragment =
                HomeFragmentDirections.actionHomeFragmentToAddTodoFragment()

        actionHomeFragmentToAddTodoFragment.setTitle("Wow!!!")
        actionHomeFragmentToAddTodoFragment.setDescription(" Description")
        actionHomeFragmentToAddTodoFragment.setHeading("Heading")

        NavHostFragment.findNavController(this)
                .navigate(actionHomeFragmentToAddTodoFragment)
    }


    override fun onResume() {
        super.onResume()
        Log.d("HomeFragment", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("HomeFragment", "onPause")
    }
}
