package com.example.todoapp.model

import com.example.todoapp.data.Database

data class TaskUiState(
    val list: List<Task> = Database.TodoList
)