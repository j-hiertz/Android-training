package com.example.tp_2.controller

import com.example.tp_2.model.book.Book

interface Search {

    fun searchByTitle(title: String) : Book?

    fun searchByAuthor(authorName: String) : Book?
}