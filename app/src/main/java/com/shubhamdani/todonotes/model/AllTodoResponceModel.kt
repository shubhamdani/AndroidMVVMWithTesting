package com.shubhamdani.todonotes.model

data class AllTodoResponceModel(
    val message: String,
    val responseModel: List<TodoModel>,
    val status: Int
)
