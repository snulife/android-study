package com.example.todoapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.todoapp.R

val dijkstra = FontFamily(
    Font(R.font.dijkstra, FontWeight.Normal)
)

val comfortaa_bold = FontFamily(
    Font(R.font.comfortaa_bold, FontWeight.ExtraBold)
)

val comfortaa_light = FontFamily(
    Font(R.font.comfortaa_light, FontWeight.Light)
)

val comfortaa_medium = FontFamily(
    Font(R.font.comfortaa_medium, FontWeight.Medium)
)

val comfortaa_semibold = FontFamily(
    Font(R.font.comfortaa_semibold, FontWeight.SemiBold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = comfortaa_bold,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 1.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = comfortaa_semibold,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.8.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = comfortaa_medium,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodySmall = TextStyle(
        fontFamily = comfortaa_light,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.3.sp
    ),
)