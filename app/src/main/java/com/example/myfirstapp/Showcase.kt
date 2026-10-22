package com.example.myfirstapp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Showcase(onAppClick: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { 3 })

    Column {
        Text(
            text = "Витрина",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            Column {
                // Страница 0: индексы 0–2, страница 1: 3–5, страница 2: 6–8.
                for (index in page * 3 until page * 3 + 3) {
                    AppItem(app = showcaseApps[index], onClick = onAppClick)
                    HorizontalDivider()
                }
            }
        }
        Text(
            text = "${pagerState.currentPage + 1} / 3",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}
