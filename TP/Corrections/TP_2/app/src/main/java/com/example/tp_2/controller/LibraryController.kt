package com.example.tp_2.controller

import android.util.Log
import com.example.tp_2.model.book.Book
import com.example.tp_2.model.book.BookStatus
import com.example.tp_2.model.book.Category
import com.example.tp_2.model.book.DigitalBook
import com.example.tp_2.model.book.PaperBook
import com.example.tp_2.model.library.Library
import com.example.tp_2.model.user.User
import java.util.Locale

object LibraryController : Search {

    private val library = Library()

    init {
        parseBooks()
    }

    override fun searchByTitle(title: String): Book? {
        return library.availableBooks.firstOrNull { it.title.contains(title, ignoreCase = true) }
    }

    override fun searchByAuthor(authorName: String): Book? {
        return library.availableBooks.firstOrNull { it.author.contains(authorName, ignoreCase = true) }
    }

    fun borrowBook(user: User, book: Book) {
        when (book) {
            is DigitalBook -> {
                user.downloadedDigitalBooks.add(book)
                book.downloadNumbers += 1
                Log.d("LOG", "L'utilisateur ${user.name} a téléchargé ${user.downloadedDigitalBooks.size} livre(s)")
                Log.d("LOG", "Le livre ${book.title} a été téléchargé ${book.downloadNumbers} fois")
            }

            is PaperBook -> {
                if (book.bookStatus == BookStatus.BORROWED) {
                    Log.d("LOG", "Le livre ${book.title} est déjà emprunté")
                    return
                }

                user.borrowedPaperBooks.add(book)
                book.bookStatus = BookStatus.BORROWED

                Log.d("LOG", "L'utilisateur ${user.name} a emprunté ${user.borrowedPaperBooks.size} livre(s)")
            }
        }
    }

    fun returnBook(user: User, book: Book) {
        when (book) {
            is DigitalBook -> {
                Log.d("LOG", "Pas besoin de rendre un livre téléchargé...")
            }

            is PaperBook -> {
                if (book.bookStatus == BookStatus.AVAILABLE) {
                    Log.d("LOG", "${book.title} est déjà disponible")
                    return
                }

                user.borrowedPaperBooks.remove(book)
                book.bookStatus = BookStatus.AVAILABLE

                Log.d("LOG", "L'utilisateur ${user.name} a rendu ${book.title}")
            }
        }
    }

    private fun parseBooks() {
        Log.d("LOG", "Parsing books...")

        books.lines().forEach { line ->
            val parts = line.split("|")
            val title = parts[0].trim()
            val author = parts[1].trim()
            val year = parts[2].trim().toInt()
            val pages = parts.getOrNull(3)?.trim()?.toIntOrNull() ?: 0

            val book = when (parts[4].trim().uppercase(Locale.ROOT)) {
                "PAPER" -> {
                    PaperBook(
                        title = title,
                        author = author,
                        publicationYear = year,
                        category = Category.FANTASY,
                        numberOfPages = pages,
                    )
                }

                else -> {
                    DigitalBook(
                        title = title,
                        author = author,
                        publicationYear = year,
                        category = Category.FANTASY
                    )
                }
            }

            Log.d("LOG", "$book")
            library.addBook(book)
        }

        Log.d("LOG", "Ajout de ${library.availableBooks.size} livres")
        Log.d("LOG", "-----------------------------")
        Log.d("LOG", "-----------------------------")
        Log.d("LOG", "-----------------------------")
    }
}

private val books = """
    Le Hobbit|J.R.R. Tolkien|1937|300|PAPER
    1984|George Orwell|1949||DIGITAL
    Ne tirez pas sur l'oiseau moqueur|Harper Lee|1960|281|PAPER
    Le Meilleur des mondes|Aldous Huxley|1932||DIGITAL
    Moby Dick|Herman Melville|1851|585|PAPER
    L'Attrape-cœurs|J.D. Salinger|1951|214|PAPER
    Fahrenheit 451|Ray Bradbury|1953||DIGITAL
    Gatsby le Magnifique|F. Scott Fitzgerald|1925|180|PAPER
    Harry Potter à l'école des sorciers|J.K. Rowling|1997|223|PAPER
    Le Portrait de Dorian Gray|Oscar Wilde|1890||DIGITAL
    Les Misérables|Victor Hugo|1862|1232|PAPER
    L'Étranger|Albert Camus|1942|123|PAPER
    Le Petit Prince|Antoine de Saint-Exupéry|1943|96|PAPER
    Crime et Châtiment|Fiodor Dostoïevski|1866|430|PAPER
    Le Comte de Monte-Cristo|Alexandre Dumas|1844|1276|PAPER
    Vingt mille lieues sous les mers|Jules Verne|1870|374|PAPER
    L'Odyssée|Homère|-800|300|PAPER
    Les Fleurs du mal|Charles Baudelaire|1857||DIGITAL
    Le Rouge et le Noir|Stendhal|1830|432|PAPER
    Cien años de soledad|Gabriel García Márquez|1967||DIGITAL
    L'Insoutenable légèreté de l'être|Milan Kundera|1984|320|PAPER
""".trimIndent()