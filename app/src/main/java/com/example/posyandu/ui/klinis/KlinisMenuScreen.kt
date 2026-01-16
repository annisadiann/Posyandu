package com.example.posyandu.ui.klinis

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun KlinisMenuScreen(
    onNavigateBack: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Ini adalah halaman menu Klinis cadangan.")
        Button(onClick = onNavigateBack) {
            Text("Kembali")
        }
    }
}