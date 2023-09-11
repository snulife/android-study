package com.example.todoapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.model.Priority
import com.example.todoapp.model.Task
import com.example.todoapp.model.TaskUiState
import com.example.todoapp.ui.theme.comfortaa_bold
import com.example.todoapp.ui.theme.comfortaa_light
import com.example.todoapp.ui.theme.dijkstra

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoApp() {
    val viewModel: TaskViewModel = viewModel()
    Scaffold(
        topBar = { TodoTopAppBar() },
        containerColor = Color.White,
        contentColor = Color.Black

    ) {
        val uiState by viewModel.uiState.collectAsState()

        TodoScreen(modifier = Modifier.padding(it), viewModel, uiState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoTopAppBar() = TopAppBar(
    title = {
        Text(
            "Todo App",
            fontFamily = comfortaa_bold,
            color = Color.Black,
            fontWeight = FontWeight.ExtraBold
        )
    },
    colors = TopAppBarDefaults.smallTopAppBarColors(Color.White),
)

@Composable
fun TodoScreen(modifier: Modifier = Modifier, viewModel: TaskViewModel, uiState: TaskUiState) {
    Column(
        modifier
            .fillMaxSize()
            .padding(top = 20.dp), //horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween
    ) {
        var assertModal by remember { mutableStateOf(false) }
        val sortedList = uiState.list.sortedBy { task ->
            when (task.priority) {
                Priority.HIGH -> 0
                Priority.NORMAL -> 1
                Priority.LOW -> 2
            }
        }

        fun triggerModal() {
            assertModal = !assertModal
        }

        if (assertModal) {
            AddTaskModal(viewModel) { triggerModal() }
        }
        TodoList(
            uiState,
            sortedList,
            triggerModal = { triggerModal() },
            resetList = viewModel::resetList,
            doneTask = viewModel::doneTask,
            deleteTask = viewModel::deleteTask,
        )
    }
}

@Composable
fun TodoList(
    uiState: TaskUiState,
    sortedList: List<Task>,
    triggerModal: () -> Unit,
    resetList: () -> Unit,
    doneTask: (Task) -> Unit,
    deleteTask: (Task) -> Unit,
) {
    if (uiState.list.isEmpty()) {
        EmptyWelcome(triggerModal, resetList)
    } else {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFFFFFFF),
                                Color(0xFFA3B3FF)
                            )
                        )
                    )
                    .padding(start = 10.dp, end = 20.dp, bottom = 20.dp)
                    .weight(1f)
            ) {
                items(sortedList) { task ->
                    Row(
                        modifier = Modifier.heightIn(min = 50.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = task.done,
                            onCheckedChange = { doneTask(task) },
                            colors = CheckboxDefaults.colors(Color(0xFF6200EE))
                        )
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = task.task,
                                fontFamily = dijkstra,
                                fontSize = 15.sp,
                                modifier = Modifier.weight(1f),
                                textDecoration = if (task.done) {
                                    TextDecoration.LineThrough
                                } else {
                                    TextDecoration.None
                                }
                            )
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(
                                        color =
                                        when (task.priority) {
                                            Priority.HIGH -> Color.Red
                                            Priority.NORMAL -> Color(0xEDFFCD50)
                                            Priority.LOW -> Color.Green
                                        }
                                    )
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Box(modifier = Modifier
                            .size(20.dp)
                            .clickable { deleteTask(task) }
                            .border(width = 1.dp, color = Color.Black),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "X", fontWeight = FontWeight.Medium, fontSize = 10.sp)
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFA3B3FF),
                                Color(0xFFBAF5FF)
                            )
                        )
                    )
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                AddButton { triggerModal() }
                ResetText(modifier = Modifier, resetList)
            }
        }
    }
}

@Composable
fun AddButton(assertModal: () -> Unit) {
    Button(
        onClick = { assertModal() },
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF6200EE),
            contentColor = Color(0xFFffffff)
        )
    ) {
        Text(text = "+", fontWeight = FontWeight.Bold, fontSize = 40.sp)
    }
}

@Composable
fun ResetText(modifier: Modifier = Modifier, resetList: () -> Unit) {
    Spacer(modifier = modifier.height(15.dp))
    Text(
        text = "Reset",
        fontFamily = comfortaa_light,
        textDecoration = TextDecoration.Underline,
        modifier = modifier.clickable { resetList() }
    )

    Column {
        Text(
            text = "Reset",
            fontFamily = comfortaa_light,
            textDecoration = TextDecoration.Underline,
            modifier = modifier.align(
                Alignment.CenterHorizontally
            )
        )
    }
}