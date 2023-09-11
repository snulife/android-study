package com.example.todoapp.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todoapp.model.Priority
import com.example.todoapp.model.Task
import com.example.todoapp.model.TaskUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {
    //    private val _uiState = MutableStateFlow(TaskUiState())
//    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()
    val list = mutableStateListOf<Task>()

    init {
        list.addAll(
            listOf(
                Task("Go to the GYM", Priority.HIGH, false),
                Task("Take a nap", Priority.NORMAL, false),
                Task("Play Arena mode in LOL", Priority.LOW, false),
                Task("Buy a milk, a new toy, and cereals", Priority.NORMAL, false)
            )
        )
    }


    fun addTask(newTask: String, priority: Priority) {
        _uiState.update { currentState ->
            currentState.copy(
                list = currentState.list + Task(newTask, priority, false)
            )
//            currentState.copy(
//                list = currentState.list
//            )
        }
    }

    fun deleteTask(deleteTask: Task) {
//        val taskIndex = _uiState.value.list.indexOf(deleteTask)
        _uiState.update { currentState ->
//            currentState.list.removeAt(taskIndex)
            currentState.copy(
                list = currentState.list.filter {
                    it != deleteTask
                }
            )
        }
    }

    fun doneTask(doneTask: Task) {
        val taskIndex = _uiState.value.list.indexOf(doneTask)
        val changedTask = Task(doneTask.task, doneTask.priority, !doneTask.done)
        _uiState.update { currentState ->
            currentState.copy(
                list = currentState.list.map {
                    if (it == doneTask) {
                        changedTask
                    } else {
                        it
                    }
                }
            )
//            currentState.list.removeAt(taskIndex)
//            currentState.list.add(changedTask)
            currentState.copy(
                list = currentState.list
            )
        }
    }

    fun resetList() {
        _uiState.update { currentState -> currentState.copy(list = mutableListOf()) }
    }
}