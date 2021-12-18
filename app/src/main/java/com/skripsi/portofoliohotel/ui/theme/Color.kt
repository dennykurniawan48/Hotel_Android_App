package com.skripsi.portofoliohotel.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.ContentAlpha
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val LightGray = Color(0xFF868686)
val DarkGray = Color(0xFF141414)

val Colors.OnBoardColor
    @Composable
    get() = if(isLight) Color.White else DarkGray

val Colors.TitleOnBoardColor
@Composable
get() = if(isLight) DarkGray else Color.White

val Colors.DescOnBoardColor
@Composable
get() = if(isLight) DarkGray.copy(alpha = ContentAlpha.medium) else Color.White.copy(alpha = ContentAlpha.medium)

val Colors.ActiveIndicator
@Composable
get() = if(isLight) Purple500 else Purple700

val Colors.InactiveIndicator
    @Composable
    get() = if(isLight) LightGray else Color.White