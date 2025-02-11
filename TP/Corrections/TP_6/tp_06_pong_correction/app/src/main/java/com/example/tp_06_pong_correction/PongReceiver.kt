package com.example.tp_06_pong_correction

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class PongReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("message") ?: "Aucun message reçu"
        Log.d("PongReceiver", "Message reçu de Ping : $message")
    }
}