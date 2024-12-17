package com.example.tp08_squeleton.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tp08_squeleton.R
import com.example.tp08_squeleton.ui.components.RecipeListItem
import com.example.tp08_squeleton.ui.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(
    recipeViewModel: RecipeViewModel,
    recipes: List<RecipeWithIngredients>,
    onCreateRecipeClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.recipe_list)) },
                actions = {
                    IconButton(onClick = onCreateRecipeClicked) {
                        Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.create_recipe))
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .padding(paddingValues)
            ) {
                if (recipes.isEmpty()) {
                    Text(text = stringResource(R.string.no_recipes_available), style = MaterialTheme.typography.titleMedium)
                } else {
                    LazyColumn {
                        items(recipes) { recipeWithIngredients ->
                            RecipeListItem(
                                recipeWithIngredients,
                                onDelete = {
                                    recipeViewModel.deleteRecipeWithIngredients(recipeWithIngredients.recipe)
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}