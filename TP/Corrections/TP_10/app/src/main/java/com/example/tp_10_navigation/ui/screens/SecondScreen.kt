package com.example.tp_10_navigation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.tp_10_navigation.ui.navigation.SecondRoute

@Composable
fun SecondScreen(
    secondRoute: SecondRoute,
    onSettingsClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "${secondRoute.name} : ${secondRoute.age}")
        Button(onClick = onSettingsClick) {
            Text("Open settings")
        }
    }
}