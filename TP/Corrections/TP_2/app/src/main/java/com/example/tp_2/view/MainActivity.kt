package com.example.tp_2.view

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tp_2.R
import com.example.tp_2.controller.LibraryController
import com.example.tp_2.model.user.User

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val user = User(name = "Android")
        val user1 = User(name = "Toto")

        LibraryController.searchByTitle("hobb")?.let {
            LibraryController.borrowBook(user, it)
            LibraryController.borrowBook(user1, it)
        }

        LibraryController.searchByAuthor("George")?.let {
            LibraryController.borrowBook(user, it)
            LibraryController.borrowBook(user1, it)
        }

        Log.d("LOG", "-----------------------------")
        Log.d("LOG", "-----------------------------")
        Log.d("LOG", "-----------------------------")

        LibraryController.searchByTitle("hobb")?.let {
            LibraryController.returnBook(user, it)
        }

        LibraryController.searchByAuthor("George")?.let {
            LibraryController.returnBook(user, it)
        }
    }
}