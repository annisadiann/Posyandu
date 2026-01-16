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
import androidx.compose.ui.text.font.FontStyle
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
    val totalTerdaftar by viewModel.statsDashboard.collectAsState()
    // Mengambil data jadwal untuk mengisi kekosongan visual
    val listJadwal by viewModel.allJadwal.collectAsState()

    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val greeting = when (hour) {
        in 0..11 -> "Selamat Pagi"
        in 12..14 -> "Selamat Siang"
        in 15..18 -> "Selamat Sore"
        else -> "Selamat Malam"
    }

    val tanggalHariIni = SimpleDateFormat("EEEE, d MMMM yyyy", Locale("id", "ID")).format(Date())

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

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        StatCard(
                            label = "Diperiksa",
                            value = "$totalTerdaftar",
                            icon = Icons.Default.CheckCircle,
                            modifier = Modifier.weight(1f)
                        )
                        StatCard(
                            label = "Agenda",
                            value = "${listJadwal.size}",
                            icon = Icons.Default.Notifications,
                            modifier = Modifier.weight(1f)
                        )
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

            // GRID MENU
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                item {
                    MenuCard("Data Pasien", Icons.Default.Face, Color(0xFFFFD1DC)) { onMenuSelected(Screen.IbuBalita.route) }
                }
                item {
                    MenuCard("Layanan", Icons.Default.Favorite, Color(0xFFF8BBD0)) { onMenuSelected(Screen.LayananKlinis.route) }
                }
                item {
                    MenuCard("Laporan", Icons.Default.List, Color(0xFFF06292)) { onMenuSelected(Screen.Laporan.route) }
                }
            }

            Card(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE4E1).copy(alpha = 0.5f)),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(1.dp, Color(0xFFFF69B4).copy(alpha = 0.2f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Info, null, tint = Color(0xFFFF1493), modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Tips: Pastikan data berat badan balita selalu diperbarui setiap bulan.",
                        fontSize = 12.sp,
                        color = Color(0xFF880E4F),
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }
}

@Composable
fun StatCard(label: String, value: String, icon: ImageVector, modifier: Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f)),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(shape = CircleShape, color = Color.White.copy(alpha = 0.2f), modifier = Modifier.size(32.dp)) {
                Icon(icon, null, tint = Color.White, modifier = Modifier.padding(6.dp))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(label, color = Color.White.copy(alpha = 0.8f), fontSize = 10.sp)
                Text(value, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
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
            .height(120.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(4.dp),
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
                modifier = Modifier.size(44.dp)
            ) {
                Icon(icon, title, modifier = Modifier.padding(10.dp), tint = Color(0xFF880E4F))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, fontWeight = FontWeight.Bold, color = Color(0xFF880E4F), fontSize = 13.sp)
        }
    }
}