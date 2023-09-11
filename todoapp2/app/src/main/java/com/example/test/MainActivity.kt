package com.example.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class TodoState(
    val title: String,
    val isChecked: Boolean,
    val id: Int
)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // 데이터를 저장하는 부분
            var todoItems by remember {
                mutableStateOf(
                    listOf(
                        TodoState("맥주병 모으기", false, 0),
                        TodoState("코틀린 공부하기", false, 1),
                        TodoState("안드로이드 공부하기", false, 2)
                    )
                )
            }
            val showDialog = remember { mutableStateOf(false) }

            // 화면을 그리는 부분
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                item {
                    Text("TODO APP")
                }
                items(todoItems, key = { it.id }) {
                    TodoItem(
                        title = it.title,
                        checked = it.isChecked,
                        onCheckedChange = { isChecked ->
                            todoItems = todoItems.map { todoItem ->
                                if (todoItem == it) {
                                    todoItem.copy(isChecked = isChecked)
                                } else {
                                    todoItem
                                }
                            }
                        },
                        onClick = {
                            showDialog.value = true
                        }
                    )
                }
                item {
                    Button(onClick = {
                        todoItems = todoItems + TodoState("새로운 할 일", false, todoItems.size)
                    }) {
                        Text("추가하기")
                    }
                }
            }
            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    title = { Text(text = "Modify Item") },
                    text = { Text(text = "Modify the item here.") },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                // Handle the dialog confirmation here
                                showDialog.value = false
                            }
                        ) {
                            Text(text = "Save")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showDialog.value = false }
                        ) {
                            Text(text = "Cancel")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun TodoItem(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(color = Color(0xFF67E2FD))
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Text(title, modifier = Modifier.align(Alignment.CenterStart))
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}