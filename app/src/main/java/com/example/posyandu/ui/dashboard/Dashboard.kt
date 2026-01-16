package com.example.posyandu.ui.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.posyandu.navigation.Screen
import com.example.posyandu.viewmodel.IbuBalitaViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: IbuBalitaViewModel,
    onMenuSelected: (String) -> Unit
) {
    // Mengambil data statistik stabil (Total keseluruhan)
    val totalTerdaftar by viewModel.statsDashboard.collectAsState()

    // Logika Greeting (Menyapa Pagi/Siang/Sore/Malam)
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val greeting = when (hour) {
        in 0..11 -> "Selamat Pagi"
        in 12..14 -> "Selamat Siang"
        in 15..18 -> "Selamat Sore"
        else -> "Selamat Malam"
    }

    // Format tanggal hari ini
    val sdf = SimpleDateFormat("EEEE, d MMMM yyyy", Locale("id", "ID"))
    val tanggalHariIni = sdf.format(Date())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Posyandu Digital", color = Color.White, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFF1493)),
                actions = {
                    IconButton(onClick = { onMenuSelected("logout") }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Logout", tint = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFFFFAFA))
        ) {
            // BOX HEADER DENGAN GRADASI (Header melengkung)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFFFF1493), Color(0xFFFF69B4))
                        ),
                        shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)
                    )
                    .padding(horizontal = 24.dp, vertical = 32.dp)
            ) {
                Column {
                    Text("$greeting, Admin!", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.ExtraBold)
                    Text(tanggalHariIni, color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)

                    Spacer(modifier = Modifier.height(24.dp))

                    // CARD STATISTIK (Glassmorphism style sederhana)
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f)),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = Color.White.copy(alpha = 0.2f),
                                modifier = Modifier.size(44.dp)
                            ) {
                                // Pakai ikon CheckCircle (pasti ada)
                                Icon(
                                    Icons.Default.CheckCircle,
                                    null,
                                    tint = Color.White,
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                            Spacer(Modifier.width(16.dp))
                            Column {
                                Text("Total Balita Diperiksa", color = Color.White.copy(alpha = 0.9f), fontSize = 12.sp)
                                Text(
                                    text = "$totalTerdaftar Balita",
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Menu Utama",
                modifier = Modifier.padding(horizontal = 24.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF880E4F)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    MenuCard(
                        title = "Data Ibu/Balita",
                        icon = Icons.Default.Face, // Ikon Standar
                        color = Color(0xFFFFD1DC),
                        onClick = { onMenuSelected(Screen.IbuBalita.route) }
                    )
                }
                item {
                    MenuCard(
                        title = "Pemeriksaan",
                        icon = Icons.Default.Favorite, // Ikon Standar
                        color = Color(0xFFF8BBD0),
                        onClick = { onMenuSelected(Screen.LayananKlinis.route) }
                    )
                }
                item {
                    MenuCard(
                        title = "Laporan",
                        icon = Icons.Default.List, // Ikon Standar
                        color = Color(0xFFF06292),
                        onClick = { onMenuSelected(Screen.Laporan.route) }
                    )
                }
            }
        }
    }
}

@Composable
fun MenuCard(title: String, icon: ImageVector, color: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(130.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.4f),
                modifier = Modifier.size(50.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    modifier = Modifier.padding(12.dp),
                    tint = Color(0xFF880E4F)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF880E4F),
                fontSize = 14.sp
            )
        }
    }
}