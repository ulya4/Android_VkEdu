package com.example.myfirstapp.appdetails

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.myfirstapp.AppInfo

// Структура карточки из практики, данные заданы в коде.
data class AppDetails(
    val name: String,
    val developer: String,
    val category: String,
    val ageRating: Int,
    val size: Float,
    val icon: Int,
    val description: String,
    val screenshotUrlList: List<String>
)

@Composable
fun AppDetailsScreen(
    app: AppInfo,
    onBackClick: () -> Unit,
    onShowcaseClick: () -> Unit
) {
    val context = LocalContext.current
    var descriptionExpanded by rememberSaveable { mutableStateOf(false) }
    val details = AppDetails(
        name = app.name,
        developer = "Учебный разработчик",
        category = app.category,
        ageRating = 0,
        size = 24f,
        icon = app.icon,
        description = "${app.description}. Это учебная карточка приложения ${app.name}. " +
            "Все данные заданы в коде для практического задания.",
        // Учебные скриншоты из карточки VkEducation.
        screenshotUrlList = listOf(
            "https://static.rustore.ru/apk/393868735/content/SCREENSHOT/dfd33017-e90d-4990-aa8c-6f159d546788.jpg",
            "https://static.rustore.ru/apk/393868735/content/SCREENSHOT/60ec4cbc-dcf6-4e69-aa6f-cc2da7de1af6.jpg",
            "https://static.rustore.ru/apk/393868735/content/SCREENSHOT/c2dde8bc-c4ab-482a-80a5-2789149f598d.jpg"
        )
    )

    AppDetailsContent(
        appDetails = details,
        descriptionExpanded = descriptionExpanded,
        onBackClick = onBackClick,
        onShareClick = {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, app.name)
            }
            context.startActivity(Intent.createChooser(intent, "Поделиться"))
        },
        onInstallClick = {
            Toast.makeText(context, "Учебный экран: установка не выполняется", Toast.LENGTH_SHORT).show()
        },
        onReadMoreClick = { descriptionExpanded = true },
        onDeveloperClick = {
            Toast.makeText(context, details.developer, Toast.LENGTH_SHORT).show()
        },
        onShowcaseClick = onShowcaseClick
    )
}

// Панель из практики адаптирована под текстовые кнопки.
@Composable
fun Toolbar(onBackClick: () -> Unit, onShareClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(onClick = onBackClick) { Text("Назад") }
        TextButton(onClick = onShareClick) { Text("Поделиться") }
    }
}
