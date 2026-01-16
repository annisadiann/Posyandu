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
    // File ini sekarang memiliki nama fungsi yang unik (KlinisMenuScreen)
    // Sehingga tidak akan menyebabkan "Conflicting Overloads" dengan LayananKlinisScreen
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Ini adalah halaman menu Klinis cadangan.")
        Button(onClick = onNavigateBack) {
            Text("Kembali")
        }
    }
}