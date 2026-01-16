package com.example.posyandu.ui.klinis

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayananKlinisScreen(
    onNavigateToPemeriksaan: () -> Unit,
    onNavigateToJadwal: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Layanan Klinis", color = Color.White, fontWeight = FontWeight.ExtraBold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Kembali", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFF1493))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFFFFAFA))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFFFF1493), Color(0xFFFF69B4))
                        ),
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    )
                    .padding(24.dp)
            ) {
                Column {
                    Text("Pilih Kategori", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("Kelola kesehatan & jadwal balita", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                LayananCard(
                    title = "Pemeriksaan Balita",
                    subtitle = "Input BB, TB, dan Umur Balita",
                    icon = Icons.Default.AddCircle,
                    iconColor = Color(0xFFFF1493),
                    containerColor = Color(0xFFFFE4E1),
                    onClick = onNavigateToPemeriksaan
                )

                LayananCard(
                    title = "Jadwal Kontrol",
                    subtitle = "Lihat agenda kunjungan rutin",
                    icon = Icons.Default.DateRange,
                    iconColor = Color(0xFF00ACC1),
                    containerColor = Color(0xFFE0F7FA),
                    onClick = onNavigateToJadwal
                )
            }
        }
    }
}

@Composable
fun LayananCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    containerColor: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.6f),
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.padding(12.dp),
                    tint = iconColor
                )
            }

            Spacer(Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = Color.DarkGray)
                Text(text = subtitle, fontSize = 13.sp, color = Color.DarkGray.copy(alpha = 0.7f))
            }

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Color.DarkGray.copy(alpha = 0.3f)
            )
        }
    }
}