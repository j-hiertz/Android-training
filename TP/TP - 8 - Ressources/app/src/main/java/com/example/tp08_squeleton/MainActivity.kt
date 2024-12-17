package com.example.tp08_squeleton

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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tp08_squeleton.ui.navigation.CreateRecipeNavigation
import com.example.tp08_squeleton.ui.navigation.RecipeListNavigation
import com.example.tp08_squeleton.ui.screen.CreateRecipeScreen
import com.example.tp08_squeleton.ui.screen.RecipeListScreen
import com.example.tp08_squeleton.ui.theme.TP08_squeletonTheme
import com.example.tp08_squeleton.ui.viewmodel.RecipeViewModel

class MainActivity : ComponentActivity() {
    private val recipeViewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TP08_squeletonTheme {
                val navController = rememberNavController()
                val recipes by recipeViewModel.recipesWithIngredients.collectAsState()

                NavHost(
                    navController = navController,
                    startDestination = RecipeListNavigation
                ) {
                    composable<RecipeListNavigation> {
                        RecipeListScreen(
                            recipeViewModel = recipeViewModel,
                            recipes = recipes,
                            onCreateRecipeClicked = { navController.navigate(CreateRecipe) }
                        )
                    }

                    composable<CreateRecipeNavigation>(
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