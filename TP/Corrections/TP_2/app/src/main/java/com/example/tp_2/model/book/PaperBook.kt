package com.example.tp_2.model.book

class PaperBook(
    title: String,
    author: String,
    publicationYear: Int,
    category: Category,
    var numberOfPages: Int,
    var bookStatus: BookStatus = BookStatus.AVAILABLE
) : Book(title, author, publicationYear, category)