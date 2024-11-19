package com.example.tp_03_correction

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private val takePictureLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            (result.data?.extras?.get("data") as? Bitmap)?.let {
                findViewById<ImageView>(R.id.photo).setImageBitmap(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val newActivityButton = findViewById<MaterialButton>(R.id.button_new_activity)

        newActivityButton.setOnClickListener {
            val intent = Intent(this, Activity2::class.java)
            startActivity(intent)
        }

        findViewById<MaterialButton>(R.id.button_navigator).setOnClickListener {
            val url = "https://developer.android.com/?hl=fr"
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
            }

            verifyAndDispatchIntent(intent)
        }

        findViewById<MaterialButton>(R.id.button_email).setOnClickListener {
            val email = "example@example.com"

            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                putExtra(Intent.EXTRA_SUBJECT, "Subject")
                putExtra(Intent.EXTRA_TEXT, "Message...")
            }

            verifyAndDispatchIntent(intent)
        }

        findViewById<MaterialButton>(R.id.button_photo).setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePictureLauncher.launch(takePictureIntent)
        }
    }

    private fun verifyAndDispatchIntent(intent: Intent) {
        // This code should work to check that an application can respond to the request, but this is not the case for emails...

//        if (intent.resolveActivity(packageManager) != null) {
//            startActivity(intent)
//        }

        try {
            startActivity(intent)
        } catch (ex: Exception) {
            Toast.makeText(this, "Unable to open application", Toast.LENGTH_LONG).show()
        }
    }
}