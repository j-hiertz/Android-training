package com.example.tp_08_correction.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.tp_08_correction.R
import com.example.tp_08_correction.entity.Ingredient
import com.example.tp_08_correction.entity.Recipe
import com.example.tp_08_correction.ui.components.RecipeTextField
import com.example.tp_08_correction.ui.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRecipeScreen(
    recipeViewModel: RecipeViewModel,
    onRecipeCreated: () -> Unit
) {
    var recipeTitle by remember { mutableStateOf("") }
    var recipeDescription by remember { mutableStateOf("") }
    var preparationTime by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf(listOf<Ingredient>()) }
    var ingredientName by remember { mutableStateOf("") }
    var ingredientQuantity by remember { mutableStateOf("") }
    var ingredientUnit by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.create_recipe)) }
            )
        },
        content = { paddingValues ->

            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 20.dp)
            ) {
                RecipeTextField(
                    value = recipeTitle,
                    onValueChange = { recipeTitle = it },
                    label = { Text(stringResource(R.string.title)) },
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    modifier = Modifier.fillMaxWidth()
                )

                RecipeTextField(
                    value = recipeDescription,
                    onValueChange = { recipeDescription = it },
                    label = { Text(stringResource(R.string.description)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )

                RecipeTextField(
                    value = preparationTime,
                    onValueChange = { preparationTime = it },
                    label = { Text(stringResource(R.string.preparation_time_minutes)) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )

                Text(text = stringResource(R.string.ingredients), style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(vertical = 16.dp))

                RecipeTextField(
                    value = ingredientName,
                    onValueChange = { ingredientName = it },
                    label = { Text(stringResource(R.string.ingredient_name)) },
                    modifier = Modifier.fillMaxWidth()
                )

                RecipeTextField(
                    value = ingredientQuantity,
                    onValueChange = { ingredientQuantity = it },
                    label = { Text(stringResource(R.string.quantity)) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )

                RecipeTextField(
                    value = ingredientUnit,
                    onValueChange = { ingredientUnit = it },
                    label = { Text(stringResource(R.string.unit_e_g_grams_cups)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )

                Button(
                    onClick = {
                        val newIngredient = Ingredient(
                            name = ingredientName,
                            quantity = ingredientQuantity.toFloatOrNull() ?: 0f,
                            unit = ingredientUnit
                        )
                        ingredients = ingredients + newIngredient

                        ingredientName = ""
                        ingredientQuantity = ""
                        ingredientUnit = ""
                    },
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.End)
                ) {
                    Text(stringResource(R.string.add_ingredient))
                }

                LazyColumn(modifier = Modifier
                    .padding(top = 16.dp)
                    .weight(1f)) {
                    items(ingredients) { ingredient ->
                        Text("${ingredient.name} - ${ingredient.quantity} ${ingredient.unit}")
                    }
                }

                Button(
                    onClick = {
                        val recipe = Recipe(
                            title = recipeTitle,
                            description = recipeDescription,
                            preparationTime = preparationTime.toIntOrNull() ?: 0,
                        )

                        recipeViewModel.createRecipe(recipe, ingredients, onRecipeCreated)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text(stringResource(R.string.create_recipe))
                }
            }
        }
    )
}