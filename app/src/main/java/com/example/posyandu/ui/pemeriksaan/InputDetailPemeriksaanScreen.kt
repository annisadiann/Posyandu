package com.example.posyandu.ui.pemeriksaan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.posyandu.data.local.entity.Pemeriksaan
import com.example.posyandu.viewmodel.IbuBalitaViewModel
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputDetailPemeriksaanScreen(
    pasienId: Int,
    ibuViewModel: IbuBalitaViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToJadwal: () -> Unit
) {
    val dataPasien = ibuViewModel.allIbu.collectAsState().value.find { it.balita.id_balita == pasienId }

    var beratBadan by remember { mutableStateOf("") }
    var tinggiBadan by remember { mutableStateOf("") }
    var umurBulan by remember { mutableStateOf("") }

    var hasilDihitung by remember { mutableStateOf(false) }
    var statusGizi by remember { mutableStateOf("") }
    var isNormal by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Input Pemeriksaan", color = Color.White, fontWeight = FontWeight.ExtraBold) },
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
            // HEADER INFO PASIEN (Gaya Modern)
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
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.2f)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Surface(shape = CircleShape, color = Color.White.copy(alpha = 0.3f), modifier = Modifier.size(44.dp)) {
                            Icon(Icons.Default.Person, null, tint = Color.White, modifier = Modifier.padding(10.dp))
                        }
                        Spacer(Modifier.width(16.dp))
                        Column {
                            Text(
                                text = dataPasien?.balita?.nama_balita ?: "Nama Balita",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Text(
                                text = "Ibu: ${dataPasien?.ibu?.nama_ibu ?: "-"}",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Parameter Pertumbuhan", fontWeight = FontWeight.Bold, color = Color.DarkGray)

                OutlinedTextField(
                    value = umurBulan,
                    onValueChange = { umurBulan = it; hasilDihitung = false },
                    label = { Text("Umur (Bulan)") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Info, null, tint = Color(0xFFFF69B4)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = beratBadan,
                    onValueChange = { beratBadan = it; hasilDihitung = false },
                    label = { Text("Berat Badan (kg)") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Star, null, tint = Color(0xFFFF69B4)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = tinggiBadan,
                    onValueChange = { tinggiBadan = it; hasilDihitung = false },
                    label = { Text("Tinggi Badan (cm)") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Menu, null, tint = Color(0xFFFF69B4)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp)
                )

                // Tombol Hitung
                Button(
                    onClick = {
                        val bb = beratBadan.toDoubleOrNull() ?: 0.0
                        val tbMeter = (tinggiBadan.toDoubleOrNull() ?: 0.0) / 100.0
                        if (bb > 0 && tbMeter > 0) {
                            val imt = bb / (tbMeter * tbMeter)
                            isNormal = imt in 13.0..18.0
                            statusGizi = when {
                                imt < 13.0 -> "Gizi Buruk / Stunting"
                                imt in 13.0..15.0 -> "Gizi Kurang"
                                imt in 15.1..18.0 -> "Gizi Baik (Normal)"
                                else -> "Gizi Lebih"
                            }
                            hasilDihitung = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(54.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF607D8B))
                ) {
                    Icon(Icons.Default.Build, null) // Ikon Build/Alat
                    Spacer(Modifier.width(8.dp))
                    Text("Analisis Status Gizi", fontWeight = FontWeight.Bold)
                }

                if (hasilDihitung) {
                    Spacer(modifier = Modifier.height(8.dp))

                    // KARTU HASIL ANALISIS
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isNormal) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isNormal) Color(0xFF81C784) else Color(0xFFE57373)
                        )
                    ) {
                        Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = if (isNormal) Icons.Default.CheckCircle else Icons.Default.Warning,
                                contentDescription = null,
                                tint = if (isNormal) Color(0xFF2E7D32) else Color(0xFFC62828),
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = statusGizi,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = if (isNormal) Color(0xFF2E7D32) else Color(0xFFC62828)
                            )
                            Text(
                                text = if (isNormal) "Pertumbuhan balita sesuai grafik." else "Perlu perhatian dan jadwal kontrol!",
                                fontSize = 13.sp,
                                color = Color.DarkGray
                            )
                        }
                    }

                    // TOMBOL AKSI FINAL
                    Button(
                        onClick = {
                            val data = Pemeriksaan(
                                id_balita = pasienId,
                                id_admin = 1,
                                tanggal_pemeriksaan = Date(),
                                berat_badan = beratBadan.toDoubleOrNull() ?: 0.0,
                                tinggi_badan = tinggiBadan.toDoubleOrNull() ?: 0.0,
                                umur_balita = umurBulan.toIntOrNull() ?: 0,
                                status_gizi = statusGizi
                            )
                            ibuViewModel.insertPemeriksaan(data)
                            if (isNormal) onNavigateBack() else onNavigateToJadwal()
                        },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isNormal) Color(0xFF2E7D32) else Color(0xFFC62828)
                        )
                    ) {
                        Icon(if (isNormal) Icons.Default.Check else Icons.Default.DateRange, null)
                        Spacer(Modifier.width(10.dp))
                        Text(
                            text = if (isNormal) "Simpan & Kembali" else "Buat Jadwal Kontrol",
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}