package com.example.tp_10_navigation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    Box(modifier = Modifier
        .width(200.dp)
        .heightIn(min = 100.dp)
        .background(Color.Green)
    ) {
        Text(text = "Settings", modifier = Modifier.align(Alignment.Center))
    }
}