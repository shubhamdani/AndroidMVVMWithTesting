package com.shubhamdani.todonotes.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shubhamdani.todonotes.R
import com.shubhamdani.todonotes.model.TodoModel

class AllTodosAdapter(private val mAllTodoList: List<TodoModel>? = arrayListOf()) :
        RecyclerView.Adapter<AllTodosAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_todo,
                        parent,
                        false
                )
        )
    }

    override fun getItemCount(): Int = mAllTodoList?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = mAllTodoList?.get(position)?.heading
        holder.description.text = mAllTodoList?.get(position)?.description
    }

    class ViewHolder(val item: View) : RecyclerView.ViewHolder(item) {
        var description: TextView = item.findViewById<TextView>(R.id.description)
        var title: TextView = item.findViewById<TextView>(R.id.title)
    }
}
