package dev.yjyoon.coverist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.yjyoon.coverist.ui.theme.CoveristTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoveristTheme {
                val navController = rememberNavController()
                CoveristNavHost(
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun CoveristNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = "title", modifier = modifier) {
        composable("title") {
            TitleScreen(navController = navController)
        }
        composable("book-info-input") {
            BookInfoInputScreen(navController = navController, viewModel = hiltViewModel())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CoveristTheme {
        TitleScreen(navController = rememberNavController())
    }
}