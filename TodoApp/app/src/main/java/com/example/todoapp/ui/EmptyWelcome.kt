package com.example.todoapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.R

@Composable
fun EmptyWelcome(
    triggerModal: () -> Unit,
    resetList: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
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
            Spacer(modifier = Modifier.height(50.dp))
            Image(
                painterResource(id = R.drawable.work_passion_7879427_6280604),
                "Start Today's Workout!"
            )
            Spacer(modifier = Modifier.height(75.dp))
            Text(
                text = "Let's Start\nToday's Workout!!",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                lineHeight = 60.sp
            )
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
            AddButton(triggerModal)
            ResetText(modifier = Modifier, resetList)
        }
    }
}