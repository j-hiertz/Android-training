package com.example.tp_1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

        "EXO 1".logs()
        printUsers(users)

        "EXO 2".logs()
        println(getUserNames(users))

        "EXO 3".logs()
        println(getUsersOver30(users))

        "EXO 4".logs()
        println(groupUsersByCity(users))

        "EXO 5".logs()
        println(getTotalAge(users))

        "EXO 6".logs()
        println(sortUsersByAge(users))

        "EXO 7".logs()
        println(getAverageAge(users))

        "EXO 8".logs()
        println(getOldestUser(users))

        "EXO 9".logs()
        println(sortUsersByName(users))

        "EXO 10".logs()
        println(sortUsersByCityAndAge(users))
    }
}

fun printUsers(users: List<User>) = users.forEach {println(it)}

fun getUserNames(users: List<User>): List<String> {
    return users.map { it.name }
}

fun getUsersOver30(users: List<User>): List<User> {
    return users.filter { it.age > 30 }
}

fun groupUsersByCity(users: List<User>): Map<String, List<User>> {
    return users.groupBy { it.city }
}

fun getTotalAge(users: List<User>): Int {
    //return users.sumBy { it.age }
    return users.fold(0) { acc, user -> acc + user.age }
}

fun sortUsersByAge(users: List<User>): List<User> {
    return users.sortedBy { it.age }
}

fun getAverageAge(users: List<User>): Double {
    return users.map { it.age }.average()
}

fun getOldestUser(users: List<User>): User? {
    return users.maxBy { it.age }
}

fun sortUsersByName(users: List<User>): List<User> {
    return users.sortedBy { it.name }
}

fun sortUsersByCityAndAge(users: List<User>): List<User> {
    return users.sortedWith(compareBy(User::city, User::age))
}

data class User(val name: String, val age: Int, val city: String)

val users = listOf(
    User("Bob", 30, "Lyon"),
    User("Alice", 25, "Paris"),
    User("Charlie", 35, "Marseille"),
    User("David", 28, "Paris"),
    User("Eve", 40, "Lyon")
)

fun String.logs() {
    println()
    println("------ $this ------")
    println()
}