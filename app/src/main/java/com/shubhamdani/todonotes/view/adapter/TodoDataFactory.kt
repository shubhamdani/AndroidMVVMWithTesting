package com.shubhamdani.todonotes.view.adapter

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.shubhamdani.todonotes.TodoApplication
import com.shubhamdani.todonotes.data.TodoNotesRepository
import com.shubhamdani.todonotes.model.TodoModel


class TodoDataFactory : DataSource.Factory<Long, TodoModel>() {

    val mutableLiveData: MutableLiveData<PageKeyedDataSource<Long, TodoModel>> = MutableLiveData()
    private var feedDataSource: FeedDataSource? = null

    override fun create(): DataSource<Long, TodoModel> {
        feedDataSource = FeedDataSource(TodoNotesRepository(TodoApplication.NETWORK_SERVICE))
        mutableLiveData.postValue(feedDataSource)
        return feedDataSource as DataSource<Long, TodoModel>
    }

    fun invalideData() {
        feedDataSource?.invalidate()
    }
}