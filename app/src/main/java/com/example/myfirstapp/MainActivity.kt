package com.example.myfirstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstapp.ui.theme.MyFirstAppTheme
import com.example.myfirstapp.appdetails.AppDetailsScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyFirstAppTheme {
                Scaffold { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "apps",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("apps") {
                            AppListScreen(onAppClick = { app ->
                                navController.navigate("details/${apps.indexOf(app)}")
                            })
                        }
                        composable(
                            route = "details/{index}",
                            arguments = listOf(navArgument("index") { type = NavType.IntType })
                        ) { entry ->
                            val index = entry.arguments?.getInt("index") ?: 0
                            AppDetailsScreen(
                                app = apps[index],
                                onBackClick = { navController.popBackStack() },
                                onShowcaseClick = {
                                    navController.popBackStack("apps", inclusive = false)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AppListScreen(
    onAppClick: (AppInfo) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier.fillMaxSize()) {

        // Верхняя панель
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF6750A4))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(
                    R.drawable.ic_launcher_foreground
                ),
                contentDescription = "Иконка приложения",
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Список приложений",
                color = Color.White,
                fontSize = 20.sp
            )
        }

        // Вертикальный список
        LazyColumn {
            items(apps) { app ->
                AppItem(app = app, onClick = { onAppClick(app) })

                HorizontalDivider()
            }
        }
    }
}

// Одна и та же строка используется в списке и в витрине.
@Composable
fun AppItem(app: AppInfo, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(app.icon),
            contentDescription = app.name,
            modifier = Modifier.size(40.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = app.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = app.description,
                fontSize = 14.sp
            )

            Text(
                text = app.category,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}
