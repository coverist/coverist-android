package dev.yjyoon.coverist.ui.title

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Web
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.yjyoon.coverist.BuildConfig
import dev.yjyoon.coverist.ui.NavDestination

@Composable
fun TitleDrawer(
    navController: NavController,
    isOpen: Boolean,
    onBack: () -> Unit
) {
    BackHandler(enabled = isOpen, onBack = onBack)

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.primary)
                .padding(48.dp),
            contentAlignment = Alignment.Center
        ) {
        }
        Divider()
        LazyColumn {
            item {
                Spacer(Modifier.height(16.dp))
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .padding(vertical = 20.dp, horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Rounded.Web, contentDescription = null, tint = Color.Gray)
                    Spacer(Modifier.width(16.dp))
                    Text("웹페이지 바로가기")
                }
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate(NavDestination.OSS_DETAIL) }
                        .padding(vertical = 20.dp, horizontal = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Rounded.Info, contentDescription = null, tint = Color.Gray)
                    Spacer(Modifier.width(16.dp))
                    Text("오픈소스 라이센스")
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                "버전 ${BuildConfig.VERSION_NAME}",
                modifier = Modifier.align(Alignment.BottomStart),
                style = TextStyle(color = Color.Gray, fontWeight = FontWeight.Bold)
            )
        }
    }
}