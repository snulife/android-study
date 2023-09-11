package com.example.todoapp.model

data class Task(
    val task: String = "",
    val priority: Priority = Priority.NORMAL,
    var done: Boolean = false
)