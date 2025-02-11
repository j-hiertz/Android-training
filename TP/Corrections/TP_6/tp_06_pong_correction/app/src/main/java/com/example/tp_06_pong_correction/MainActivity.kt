package com.example.tp_06_pong_correction

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
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
import com.example.tp_06_pong_correction.ui.theme.Tp_06_pong_correctionTheme

class MainActivity : ComponentActivity() {

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tp_06_pong_correctionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)) {
                        Button(
                            modifier = Modifier.align(Alignment.Center),
                            onClick = {
                                val pongIntent = Intent().apply {
                                    component = ComponentName(
                                        "com.example.tp_06_ping_correction",
                                        "com.example.tp_06_ping_correction.PingReceiver"
                                    )
                                    putExtra("message", "Bonjour depuis Ping !")
                                }

                                sendBroadcast(pongIntent)
                            }) {
                            Text("Pong !")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Tp_06_pong_correctionTheme {
        Greeting("Android")
    }
}