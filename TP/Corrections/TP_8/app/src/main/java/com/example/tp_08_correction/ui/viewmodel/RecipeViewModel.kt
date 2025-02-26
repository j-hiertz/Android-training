package com.example.tp_08_correction.ui.viewmodel

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import com.example.tp_08_correction.database.DatabaseProvider
import com.example.tp_08_correction.database.RecipeDao
import com.example.tp_08_correction.entity.Ingredient
import com.example.tp_08_correction.entity.Recipe
import com.example.tp_08_correction.entity.RecipeWithIngredients
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.Executors

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    private val recipeDao: RecipeDao = DatabaseProvider.getDatabase(application).recipeDao()
    private val executor = Executors.newSingleThreadExecutor()

    private val _recipesWithIngredients = MutableStateFlow<List<RecipeWithIngredients>>(emptyList())
    val recipesWithIngredients: StateFlow<List<RecipeWithIngredients>> = _recipesWithIngredients

    init {
        loadRecipesWithIngredients()
    }

    private fun loadRecipesWithIngredients() {
        executor.execute {
            val recipes: List<RecipeWithIngredients> = recipeDao.getAllRecipesWithIngredients()
            _recipesWithIngredients.value = recipes
        }
    }

    fun createRecipe(recipe: Recipe, ingredients: List<Ingredient>, onSuccess: () -> Unit) {
        executor.submit {
            recipeDao.insertRecipeWithIngredients(recipe, ingredients)
            loadRecipesWithIngredients()

            Handler(Looper.getMainLooper()).post {
                onSuccess()
            }
        }
    }

    fun deleteRecipeWithIngredients(recipe: Recipe) {
        executor.submit {
            recipeDao.deleteRecipeWithIngredients(recipe)
            loadRecipesWithIngredients()
        }
    }
}