package com.example.tp_06_ping_correction

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tp_06_ping_correction.ui.theme.Tp_06_ping_correctionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tp_06_ping_correctionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)) {
                        Button(
                            modifier = Modifier.align(Alignment.Center),
                            onClick = {
                                val pingIntent = Intent().apply {
                                    component = ComponentName(
                                        "com.example.tp_06_pong_correction",
                                        "com.example.tp_06_pong_correction.PongReceiver"
                                    )
                                    putExtra("message", "Bonjour depuis Ping !")
                                }

                                sendBroadcast(pingIntent)
                            }) {
                            Text("Ping !")
                        }
                    }
                }
            }
        }
    }
}