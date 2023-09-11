package com.example.todoapp.data

import com.example.todoapp.model.Priority
import com.example.todoapp.model.Task

object Database {
    val TodoList= mutableListOf(
        Task("Go to the GYM", Priority.HIGH, false),
        Task("Take a nap", Priority.NORMAL, false),
        Task("Play Arena mode in LOL", Priority.LOW, false),
        Task("Buy a milk, a new toy, and cereals", Priority.NORMAL, false)
    )
}