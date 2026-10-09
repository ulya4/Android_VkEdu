package com.example.myfirstapp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myfirstapp.ui.theme.MyFirstAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            MyFirstAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    var text by remember { mutableStateOf("") }

                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(24.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        OutlinedTextField(
                            value = text,
                            onValueChange = { text = it },
                            label = { Text("Текст или номер телефона") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Button(
                            onClick = {
                                if (text.isBlank()) {
                                    showError()
                                } else {
                                    val intent =
                                        Intent(this@MainActivity, SecondActivity::class.java)

                                    intent.putExtra("text", text)
                                    startActivity(intent)
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Открыть вторую Activity")
                        }

                        Button(
                            onClick = {
                                if (text.isBlank()) {
                                    showError()
                                } else {
                                    val intent = Intent(
                                        Intent.ACTION_DIAL,
                                        Uri.parse("tel:$text")
                                    )

                                    try {
                                        startActivity(intent)
                                    } catch (e: ActivityNotFoundException) {
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Нет приложения для звонков",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Позвонить другу")
                        }

                        Button(
                            onClick = {
                                if (text.isBlank()) {
                                    showError()
                                } else {
                                    val sendIntent = Intent(Intent.ACTION_SEND).apply {
                                        type = "text/plain"
                                        putExtra(Intent.EXTRA_TEXT, text)
                                    }

                                    startActivity(
                                        Intent.createChooser(
                                            sendIntent,
                                            "Поделиться через..."
                                        )
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Поделиться текстом")
                        }
                    }
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(
            this,
            "Сначала введи текст",
            Toast.LENGTH_SHORT
        ).show()
    }
}