package com.example.tp_08_correction.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tp_08_correction.entity.Ingredient
import com.example.tp_08_correction.entity.Recipe

@Database(entities = [Recipe::class, Ingredient::class], version = 1)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}