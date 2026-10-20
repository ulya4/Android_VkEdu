package com.example.myfirstapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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

// Информация о приложении
data class AppInfo(
    val name: String,
    val description: String,
    val category: String,
    val icon: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyFirstAppTheme {
                Scaffold { innerPadding ->
                    AppListScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun AppListScreen(modifier: Modifier = Modifier) {

    // Данные для списка
    val apps = listOf(
        AppInfo("Камера", "Фото и видео",
            "Мультимедиа", android.R.drawable.ic_menu_camera),
        AppInfo("Галерея", "Просмотр фотографий",
            "Мультимедиа", android.R.drawable.ic_menu_gallery),
        AppInfo("Настройки", "Настройки телефона",
            "Система", android.R.drawable.ic_menu_manage),
        AppInfo("Телефон", "Звонки",
            "Связь", android.R.drawable.ic_menu_call),
        AppInfo("Карты", "Поиск маршрутов",
            "Навигация", android.R.drawable.ic_menu_compass)
    )

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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
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

                HorizontalDivider()
            }
        }
    }
}
