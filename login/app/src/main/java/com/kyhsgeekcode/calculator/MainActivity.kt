package com.kyhsgeekcode.calculator

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var id by remember {
                mutableStateOf("")
            }
            var pw by remember {
                mutableStateOf("")
            }
            Column {
                Text("로그인하세요")
                Row {
                    Column {
                        TextField(value = id, onValueChange = { id = it })
                        TextField(value = pw, onValueChange = { pw = it })
                    }
                    Button(onClick = {
                        Log.d("My app", "로그인할거야, id=$id, pw=$pw")
                    }) {
                        Text("로그인")
                    }
                }
            }
        }
    }
}