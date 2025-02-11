package com.example.tp_06_ping_correction

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class PingReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("message") ?: "Aucun message reçu"
        Log.d("PingReceiver", "Message reçu de Pong : $message")
    }
}