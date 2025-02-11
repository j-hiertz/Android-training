package com.example.tp_10_navigation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.tp_10_navigation.R
import com.example.tp_10_navigation.ui.navigation.SecondRoute

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onButtonClick: (SecondRoute) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Button(
            modifier = Modifier.align(alignment = Alignment.Center),
            onClick = {
                onButtonClick(SecondRoute(age = 20))
            }
        ) {
            Text(stringResource(R.string.navigate_to_screen_b))
        }
    }
}