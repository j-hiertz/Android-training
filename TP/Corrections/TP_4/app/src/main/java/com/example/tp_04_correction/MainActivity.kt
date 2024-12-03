package com.example.tp_04_correction

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp_04_correction.ui.theme.Purple80
import com.example.tp_04_correction.ui.theme.PurpleGrey80
import com.example.tp_04_correction.ui.theme.TP_04_CorrectionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val todoItems = mutableStateListOf(
            TodoItem(R.drawable.cat_1, "Acheter du pain", false),
            TodoItem(R.drawable.cat_2, "Faire du sport", true),
            TodoItem(R.drawable.cat_3, "Étudier pour l'examen", false),
            TodoItem(R.drawable.cat_4, "Appeler le médecin", false),
            TodoItem(R.drawable.cat_5, "Préparer le dîner", true),
            TodoItem(R.drawable.cat_1, "Lire un livre", false),
            TodoItem(R.drawable.cat_2, "Faire les courses", false),
            TodoItem(R.drawable.cat_3, "Répondre aux emails", true),
            TodoItem(R.drawable.cat_4, "Faire la lessive", false),
            TodoItem(R.drawable.cat_5, "Préparer le cours Android", false)
        )

        setContent {
            TP_04_CorrectionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .verticalScroll(rememberScrollState())
                    ) {
                        todoItems.forEachIndexed { index, todoItem ->
                            TodoItemRow(todoItem = todoItem) {
                                val item = todoItems[index]
                                todoItems[index] = item.copy(isCompleted = !item.isCompleted)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TodoItemRow(
    todoItem: TodoItem,
    modifier: Modifier = Modifier,
    toggleTodoItem: (TodoItem) -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (todoItem.isCompleted) PurpleGrey80 else Purple80
    )

    Surface(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(percent = 10))
                .clickable { toggleTodoItem(todoItem) }
        ),
        color = backgroundColor,
        shape = RoundedCornerShape(percent = 10)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CategoryImage(res = todoItem.categoryResId, modifier = Modifier.size(64.dp))
            Todo(
                title = todoItem.title, modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .weight(1f)
            )
            Checkbox(checked = todoItem.isCompleted, onCheckedChange = { toggleTodoItem(todoItem) })
        }
    }
}

@Composable
fun Todo(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        modifier = modifier
    )
}

@Composable
fun CategoryImage(res: Int, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = res),
        contentDescription = "Category 1",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun TodoPreview() {
    TP_04_CorrectionTheme {
        TodoItemRow(
            todoItem = TodoItem(
                categoryResId = R.drawable.cat_4,
                title = "RDV Garage",
                isCompleted = false
            )
        ) {

        }
    }
}