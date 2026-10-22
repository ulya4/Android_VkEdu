package com.example.myfirstapp

// Информация о приложении
data class AppInfo(
    val name: String,
    val description: String,
    val category: String,
    val icon: Int
)

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

// Девять приложений: по три на каждой странице витрины.
val showcaseApps = apps + listOf(
    AppInfo("Календарь", "События и встречи", "Инструменты", android.R.drawable.ic_menu_month),
    AppInfo("Почта", "Электронные письма", "Связь", android.R.drawable.ic_dialog_email),
    AppInfo("Заметки", "Запись важных дел", "Инструменты", android.R.drawable.ic_menu_edit),
    AppInfo("Музыка", "Прослушивание музыки", "Мультимедиа", android.R.drawable.ic_media_play)
)
