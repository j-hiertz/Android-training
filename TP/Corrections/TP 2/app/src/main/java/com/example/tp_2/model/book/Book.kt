package com.example.tp_2.model.book

abstract class Book(
    var title: String,
    var author: String,
    var publicationYear: Int,
    var category: Category
) {
    override fun toString(): String {
        return "Book($title, $author, $publicationYear, $category)"
    }
}