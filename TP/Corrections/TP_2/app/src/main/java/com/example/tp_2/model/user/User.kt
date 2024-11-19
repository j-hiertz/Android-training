package com.example.tp_2.model.user

import com.example.tp_2.model.book.DigitalBook
import com.example.tp_2.model.book.PaperBook

data class User(
    val name: String,
    val borrowedPaperBooks: MutableSet<PaperBook> = mutableSetOf(),
    val downloadedDigitalBooks: MutableSet<DigitalBook> = mutableSetOf(),
)