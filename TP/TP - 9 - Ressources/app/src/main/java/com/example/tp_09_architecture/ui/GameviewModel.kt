package com.example.tp_09_architecture.ui

class GameviewModel {

    val toto = FormatDateUseCase()

    init {
        toto("toto")
        toto.invoke("toto")
    }

}

class FormatDateUseCase {

    operator fun invoke(str: String) : String {
        return ""
    }

    operator fun invoke(number: Int): String {
        return ""
    }
}