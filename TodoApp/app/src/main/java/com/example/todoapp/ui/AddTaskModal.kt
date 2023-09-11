package com.example.todoapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.todoapp.model.Priority

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskModal(viewModel: TaskViewModel, cancelDialog: () -> Unit = {}) {
    var newTask:String by remember { mutableStateOf("") }
    val priorityList:List<String> =  listOf("Low", "Normal", "High")
    var selectedPriority by remember { mutableStateOf("Normal") }
    var priorityState by remember { mutableStateOf(2) }

    fun togglePriority(priority:String) {
        selectedPriority = priority
    }
    fun setPriority(priority: Int) {
        priorityState = priority
    }

    Dialog(onDismissRequest = { cancelDialog() }) {
        Surface(
            modifier = Modifier.size(400.dp, 300.dp),
            color = Color.White,
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.padding(start = 15.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    Text(text = "Add a new task", style = MaterialTheme.typography.bodyLarge)
                    OutlinedTextField(
                        value = newTask,
                        onValueChange = { newTask = it },
                        placeholder = {
                            Text(
                                text = "Type here!",
                                color = Color.Black.copy(alpha = 0.4f)
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(onDone = {
                            defaultKeyboardAction(ImeAction.Done)
                        })
                    )
                    Text(
                        text = "Priority",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                    Row {
                        priorityList.forEach { priority ->
                            Text(
                                text = priority, style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .border(width = 1.dp, color = Color(0xFF6200EE))
                                    .clickable {
                                        togglePriority(priority)
                                        when(priority) {
                                            "Low" -> setPriority(3)
                                            "Normal" -> setPriority(2)
                                            "High" -> setPriority(1)
                                        }
                                    }
                                    .background(
                                        color = if (selectedPriority != priority) Color(
                                            0xFFffffff
                                        ) else Color(0xFF6200EE)
                                    )
                                    .padding(5.dp)
                                    .clip(RoundedCornerShape(3.dp))
                                    .weight(1f),
                                color = if (selectedPriority != priority) Color(0xFF6200EE) else Color(
                                    0xFFffffff
                                ),
                            )
                            Spacer(modifier = Modifier.width(15.dp))
                        }
                    }
                }
                Row {
                    OutlinedButton(onClick = { cancelDialog() },
                        modifier = Modifier.weight(1f) .padding(end = 15.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFffffff), contentColor = Color(0xFF6200EE))
                    ) {
                        Text(text = "Cancel", style = MaterialTheme.typography.bodyMedium)
                    }
                    Button(
                        enabled = newTask.trimStart().isNotEmpty(),
                        onClick = { viewModel.addTask(newTask,
                        priority = when(priorityState) {
                            1 -> Priority.HIGH
                            2 -> Priority.NORMAL
                            else -> Priority.LOW
                        })
                        cancelDialog() },
                        modifier = Modifier.weight(1f) .padding(end = 15.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE), contentColor = Color(0xFFffffff))
                    ) {
                        Text(text = "Save", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}