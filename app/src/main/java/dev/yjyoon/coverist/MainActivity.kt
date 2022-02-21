package dev.yjyoon.coverist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.yjyoon.coverist.ui.theme.CoveristTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoveristTheme {
                val navController = rememberNavController()
                Scaffold { innerPadding ->
                    CoveristNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding))
                }
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
            BookInfoInputScreen()
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