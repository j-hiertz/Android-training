package com.example.tp_08_correction

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tp_08_correction.ui.navigation.CreateRecipe
import com.example.tp_08_correction.ui.navigation.RecipeList
import com.example.tp_08_correction.ui.screen.CreateRecipeScreen
import com.example.tp_08_correction.ui.screen.RecipeListScreen
import com.example.tp_08_correction.ui.theme.Tp_08_correctionTheme
import com.example.tp_08_correction.ui.viewmodel.RecipeViewModel

class MainActivity : ComponentActivity() {
    private val recipeViewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tp_08_correctionTheme {
                val navController = rememberNavController()
                val recipes by recipeViewModel.recipesWithIngredients.collectAsState()

                NavHost(
                    navController = navController,
                    startDestination = RecipeList
                ) {
                    composable<RecipeList> {
                        RecipeListScreen(
                            recipeViewModel = recipeViewModel,
                            recipes = recipes,
                            onCreateRecipeClicked = { navController.navigate(CreateRecipe) }
                        )
                    }

                    composable<CreateRecipe>(
                        enterTransition = {
                            slideInHorizontally(
                                initialOffsetX = { 1000 },
                                animationSpec = tween(durationMillis = 500)
                            ) + fadeIn(animationSpec = tween(durationMillis = 500))
                        },
                        popExitTransition = {
                            slideOutHorizontally(
                                targetOffsetX = { 1000 },
                                animationSpec = tween(durationMillis = 500)
                            ) + fadeOut(animationSpec = tween(durationMillis = 500))
                        }
                    ) {
                        CreateRecipeScreen(
                            recipeViewModel = recipeViewModel
                        ) {
                            navController.popBackStack()
                        }
                    }
                }
            }
        }
    }
}