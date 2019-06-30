package com.shubhamdani.todonotes.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shubhamdani.todonotes.R
import com.shubhamdani.todonotes.model.TodoModel

class AllTodosAdapter() :
    PagedListAdapter<TodoModel, AllTodosAdapter.ViewHolder>(diffCallback) {

    companion object {
        /**
         * This diff callback informs the PagedListAdapter how to compute list differences when new
         * PagedLists arrive.
         * <p>
         * When you add a Cheese with the 'Add' button, the PagedListAdapter uses diffCallback to
         * detect there's only a single item difference from before, so it only needs to animate and
         * rebind a single view.
         *
         * @see android.support.v7.util.DiffUtil
         */
        private val diffCallback = object : DiffUtil.ItemCallback<TodoModel>() {
            override fun areItemsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean =
                oldItem.id == newItem.id && oldItem.heading.equals(newItem.heading) && oldItem.description.equals(newItem.description)

            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             */
            override fun areContentsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean =
                oldItem.equals(newItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todoModel = getItem(position)
        holder.title.text = todoModel?.heading
        holder.description.text = todoModel?.description
    }

    class ViewHolder(val item: View) : RecyclerView.ViewHolder(item) {
        var description: TextView = item.findViewById<TextView>(R.id.description)
        var title: TextView = item.findViewById<TextView>(R.id.title)
    }
}
