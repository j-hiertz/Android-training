package com.example.tp_2.model.library

import com.example.tp_2.model.book.Book

data class Library(
    val availableBooks: MutableList<Book> = mutableListOf()
) {

    fun addBook(book: Book) = availableBooks.add(book)
    fun removeBook(book: Book) = availableBooks.remove(book)
}