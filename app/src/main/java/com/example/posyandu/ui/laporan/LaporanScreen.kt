package com.example.posyandu.ui.laporan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    val daftarPemeriksaan by viewModel.allPemeriksaan.collectAsState()

    // State untuk input tanggal
    var tanggalMulai by remember { mutableStateOf("01/01/2026") }
    var tanggalSelesai by remember { mutableStateOf("31/01/2026") }

    // Fungsi untuk memproses tanggal dan kirim ke ViewModel
    fun prosesLaporan() {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        try {
            val start = sdf.parse(tanggalMulai)?.time ?: 0L
            // Set end date ke akhir hari (23:59:59) agar data di tanggal tersebut ikut terhitung
            val end = (sdf.parse(tanggalSelesai)?.time ?: 0L) + 86399000L

            // Buat label untuk tampilan di halaman detail
            val labelStart = SimpleDateFormat("d MMM yyyy", Locale("id", "ID")).format(Date(start))
            val labelEnd = SimpleDateFormat("d MMM yyyy", Locale("id", "ID")).format(Date(end))
            val labelPeriode = "$labelStart - $labelEnd"

            viewModel.setFilterPeriode(start, end, labelPeriode)
            onNavigateToDetail(1)
        } catch (e: Exception) {
            // Tangani jika format tanggal salah (bisa tambah snackbar di sini)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Generate Laporan", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFF69B4))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Default.DateRange, null, Modifier.size(64.dp), Color(0xFFFF69B4))
            Spacer(modifier = Modifier.height(16.dp))
            Text("Pilih Periode Laporan", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFF1493))

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = tanggalMulai,
                onValueChange = { tanggalMulai = it },
                label = { Text("Tanggal Mulai (dd/mm/yyyy)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = tanggalSelesai,
                onValueChange = { tanggalSelesai = it },
                label = { Text("Tanggal Selesai (dd/mm/yyyy)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { prosesLaporan() },
                modifier = Modifier.fillMaxWidth().height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF69B4)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("GENERATE LAPORAN", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}