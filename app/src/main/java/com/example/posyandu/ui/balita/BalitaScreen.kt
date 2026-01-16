package com.example.posyandu.ui.balita

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.posyandu.data.local.entity.Balita
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalitaScreen(
    ibuId: Int, // Menerima ID Ibu dari Navigasi
    onNavigateBack: () -> Unit
) {
    // Dummy Data Balita berdasarkan id_ibu tertentu (Mockup untuk Testing UI)
    val listBalita = listOf(
        Balita(1, ibuId, "Ahmad Zaki", Date(), "Laki-laki"),
        Balita(2, ibuId, "Siti Zahra", Date(), "Perempuan")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("Daftar Anak", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("ID Ibu: $ibuId", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFF69B4))
            )
        },
        floatingActionButton = {
            // Fitur Tambah Balita
            FloatingActionButton(
                onClick = { /* Navigasi ke Form Tambah Balita */ },
                containerColor = Color(0xFFFF69B4),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Anak")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFFFFAFA))
        ) {
            if (listBalita.isEmpty()) {
                // Tampilan jika data kosong
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Text("Belum ada data anak.", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(listBalita) { balita ->
                        BalitaItem(
                            balita = balita,
                            onEditClick = { /* Navigasi ke Form Edit Balita */ }
                        )
                    }
                }
            }
        }
    }
}