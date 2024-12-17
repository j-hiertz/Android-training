package com.example.tp08_squeleton.ui.viewmodel

import android.app.Application
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.concurrent.Executors

class RecipeViewModel(application: Application) : AndroidViewModel(application) {

    private val executor = Executors.newSingleThreadExecutor()

    private val _recipesWithIngredients = MutableStateFlow<List<RecipeWithIngredients>>(emptyList())
    val recipesWithIngredients: StateFlow<List<RecipeWithIngredients>> = _recipesWithIngredients

    init {
        loadRecipesWithIngredients()
    }

    private fun loadRecipesWithIngredients() {

    }

    fun createRecipe(recipe: Recipe, ingredients: List<Ingredient>, onSuccess: () -> Unit) {

        Handler(Looper.getMainLooper()).post {
            onSuccess()
        }
    }

    fun deleteRecipeWithIngredients(recipe: Recipe) {

    }
}