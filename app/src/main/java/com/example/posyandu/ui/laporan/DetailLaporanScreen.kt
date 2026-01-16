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
import androidx.compose.ui.Modifier
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
    // Ambil data dari ViewModel yang sudah difilter di halaman sebelumnya
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
                title = { Text("Hasil Rekapitulasi", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Kembali", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.simpanLaporanKeDatabase(totalPemeriksaan, giziBaik, perluPerhatian, jadwalTerpenuhi)
                        scope.launch { snackbarHostState.showSnackbar("Laporan periode $periodeText tersimpan!") }
                    }) {
                        Icon(Icons.Default.Check, "Simpan", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFF69B4))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).background(Color(0xFFFFFAFA)).verticalScroll(rememberScrollState()).padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text("REKAP DATA KESEHATAN BALITA", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = Color(0xFFFF1493))
                    Text("Periode: $periodeText", fontSize = 12.sp, color = Color.Gray)

                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = Color(0xFFFFD1DC))
                    Spacer(modifier = Modifier.height(16.dp))

                    ReportRow("Total Balita Diperiksa", "$totalPemeriksaan Anak")
                    ReportRow("Gizi Tercukupi", "$giziBaik Anak")
                    ReportRow("Gizi Kurang", "$perluPerhatian Anak")
                    ReportRow("Jadwal Kontrol Terpenuhi", "$jadwalTerpenuhi Balita")

                    Spacer(modifier = Modifier.height(24.dp))
                    Box(modifier = Modifier.fillMaxWidth().background(Color(0xFFFFD1DC).copy(alpha = 0.3f), RoundedCornerShape(8.dp)).padding(12.dp)) {
                        Text("Catatan: Data ini diambil berdasarkan rentang tanggal yang Anda tentukan.", fontSize = 11.sp, color = Color(0xFF880E4F), fontStyle = FontStyle.Italic)
                    }
                }
            }
        }
    }
}

@Composable
fun ReportRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = label, color = Color.DarkGray, fontSize = 14.sp)
        Text(text = value, fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 14.sp)
    }
}