package com.example.tp_2.model.book

class DigitalBook(
    title: String,
    author: String,
    publicationYear: Int,
    category: Category,
    var downloadNumbers: Int = 0,
) : Book(title, author, publicationYear, category)