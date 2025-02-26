package com.example.tp_08_correction.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.tp_08_correction.entity.Ingredient
import com.example.tp_08_correction.entity.Recipe
import com.example.tp_08_correction.entity.RecipeWithIngredients

@Dao
interface RecipeDao {

    @Insert
    fun insertRecipe(recipe: Recipe): Long

    @Insert
    fun insertIngredients(ingredients: List<Ingredient>)

    @Transaction
    fun insertRecipeWithIngredients(recipe: Recipe, ingredients: List<Ingredient>) {
        val recipeId = insertRecipe(recipe)
        val ingredientsWithRecipeId = ingredients.map { it.copy(recipeId = recipeId) }
        insertIngredients(ingredientsWithRecipeId)
    }

    @Transaction
    @Query("SELECT * FROM recipe")
    fun getAllRecipesWithIngredients(): List<RecipeWithIngredients>

    @Query("SELECT * FROM ingredient WHERE recipeId = :recipeId")
    fun getIngredientsForRecipe(recipeId: Long): List<Ingredient>

    @Transaction
    fun deleteRecipeWithIngredients(recipe: Recipe) {
        val ingredients = getIngredientsForRecipe(recipe.id)
        deleteIngredients(ingredients)
        deleteRecipe(recipe)
    }

    @Delete
    fun deleteRecipe(recipe: Recipe)

    @Delete
    fun deleteIngredients(ingredients: List<Ingredient>)
}