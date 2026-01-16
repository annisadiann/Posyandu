package com.example.posyandu.ui.laporan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.posyandu.viewmodel.IbuBalitaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailLaporanScreen(
    viewModel: IbuBalitaViewModel,
    laporanId: Int,
    onNavigateBack: () -> Unit
) {
    val totalPemeriksaan by viewModel.statsTotal.collectAsState()
    val giziBaik by viewModel.statsGiziBaik.collectAsState()
    val perluPerhatian by viewModel.statsPerhatian.collectAsState()
    val jadwalTerpenuhi by viewModel.statsJadwal.collectAsState()
    val periodeText by viewModel.namaPeriode.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Hasil Rekapitulasi", color = Color.White, fontWeight = FontWeight.ExtraBold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Kembali", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.simpanLaporanKeDatabase(totalPemeriksaan, giziBaik, perluPerhatian, jadwalTerpenuhi)
                        scope.launch { snackbarHostState.showSnackbar("Laporan berhasil disimpan!") }
                    }) {
                        Icon(Icons.Default.Check, "Simpan", tint = Color.White)
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
                    Text("Ringkasan Statistik", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Text("Periode: $periodeText", color = Color.White.copy(alpha = 0.9f), fontSize = 13.sp)
                }
            }

            Column(modifier = Modifier.padding(20.dp)) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(6.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(shape = CircleShape, color = Color(0xFFFFD1DC), modifier = Modifier.size(32.dp)) {
                                Icon(Icons.Default.Info, null, tint = Color(0xFFFF1493), modifier = Modifier.padding(6.dp))
                            }
                            Spacer(Modifier.width(12.dp))
                            Text("DATA KESEHATAN BALITA", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp, color = Color(0xFF880E4F))
                        }

                        Spacer(modifier = Modifier.height(20.dp))
                        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
                        Spacer(modifier = Modifier.height(12.dp))

                        // Baris Data
                        ReportRow("Total Balita Diperiksa", "$totalPemeriksaan Anak", Color.Black)
                        ReportRow("Kondisi Gizi Baik", "$giziBaik Anak", Color(0xFF2E7D32))
                        ReportRow("Kondisi Perlu Perhatian", "$perluPerhatian Anak", Color(0xFFC62828))
                        ReportRow("Jadwal Kontrol Terpenuhi", "$jadwalTerpenuhi Balita", Color(0xFF1976D2))

                        Spacer(modifier = Modifier.height(24.dp))

                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = Color(0xFFF5F5F5),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Catatan: Laporan ini dihitung secara otomatis berdasarkan data pemeriksaan yang masuk pada rentang tanggal tersebut.",
                                modifier = Modifier.padding(12.dp),
                                fontSize = 11.sp,
                                color = Color.Gray,
                                fontStyle = FontStyle.Italic,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Pastikan data telah sesuai sebelum menekan tombol simpan di pojok kanan atas.",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}

@Composable
fun ReportRow(label: String, value: String, valueColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = Color.DarkGray, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        Surface(
            color = valueColor.copy(alpha = 0.1f),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = value,
                fontWeight = FontWeight.Bold,
                color = valueColor,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
            )
        }
    }
}