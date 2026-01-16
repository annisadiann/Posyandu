package com.example.posyandu.ui.laporan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.posyandu.viewmodel.IbuBalitaViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaporanScreen(
    viewModel: IbuBalitaViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToDetail: (Int) -> Unit
) {
    var tanggalMulai by remember { mutableStateOf("01/01/2026") }
    var tanggalSelesai by remember { mutableStateOf("31/01/2026") }

    fun prosesLaporan() {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        try {
            val start = sdf.parse(tanggalMulai)?.time ?: 0L
            val end = (sdf.parse(tanggalSelesai)?.time ?: 0L) + 86399000L

            val labelStart = SimpleDateFormat("d MMM yyyy", Locale("id", "ID")).format(Date(start))
            val labelEnd = SimpleDateFormat("d MMM yyyy", Locale("id", "ID")).format(Date(end))
            val labelPeriode = "$labelStart - $labelEnd"

            viewModel.setFilterPeriode(start, end, labelPeriode)
            onNavigateToDetail(1)
        } catch (e: Exception) {
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Generate Laporan", color = Color.White, fontWeight = FontWeight.ExtraBold) },
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
                .verticalScroll(rememberScrollState())
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
                    Text("Rekapitulasi Data", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("Pilih rentang tanggal untuk melihat laporan.", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                }
            }

            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Info, null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Gunakan format: Tgl/Bln/Thn", fontSize = 12.sp, color = Color.DarkGray)
                    }
                }

                // Input Tanggal Mulai
                OutlinedTextField(
                    value = tanggalMulai,
                    onValueChange = { tanggalMulai = it },
                    label = { Text("Tanggal Mulai") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.DateRange, null, tint = Color(0xFFFF1493)) },
                    shape = RoundedCornerShape(12.dp)
                )

                // Input Tanggal Selesai
                OutlinedTextField(
                    value = tanggalSelesai,
                    onValueChange = { tanggalSelesai = it },
                    label = { Text("Tanggal Selesai") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.DateRange, null, tint = Color(0xFFFF1493)) },
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Tombol Generate
                Button(
                    onClick = { prosesLaporan() },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF1493)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.List, null)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("LIHAT REKAPITULASI", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}