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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.yjyoon.coverist.ui.theme.CoveristTheme

class MainActivity : ComponentActivity() {
    private val bookInfoInputViewModel by viewModels<BookInfoInputViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoveristTheme {
                val navController = rememberNavController()
                CoveristNavHost(
                    navController = navController,
                    bookInfoInputViewModel = bookInfoInputViewModel
                )
            }
        }
    }
}

@Composable
fun CoveristNavHost(
    navController: NavHostController,
    bookInfoInputViewModel: BookInfoInputViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = "title", modifier = modifier) {
        composable("title") {
            TitleScreen(navController = navController)
        }
        composable("book-info-input") {
            BookInfoInputScreen(viewModel = bookInfoInputViewModel, navController = navController)
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