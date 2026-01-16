package com.example.posyandu.ui.pemeriksaan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    pasienId: Int, // Ini adalah id_balita
    ibuViewModel: IbuBalitaViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToJadwal: () -> Unit
) {
    // Perbaikan: Mencari di allIbu yang sekarang bertipe List<BalitaWithIbu>
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
                title = { Text("Input Pemeriksaan", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Kembali", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFF69B4))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Info Pasien
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFE4E1))
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = Color(0xFFFF69B4))
                    Spacer(Modifier.width(12.dp))
                    Column {
                        // Perbaikan: Akses lewat it.balita atau it.ibu
                        Text(
                            text = "Pasien: ${dataPasien?.balita?.nama_balita ?: "Tidak Ditemukan"}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "Ibu: ${dataPasien?.ibu?.nama_ibu ?: "-"}",
                            fontSize = 14.sp
                        )
                    }
                }
            }

            // Input Fields
            OutlinedTextField(
                value = umurBulan,
                onValueChange = { umurBulan = it; hasilDihitung = false },
                label = { Text("Umur (Bulan)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            OutlinedTextField(
                value = beratBadan,
                onValueChange = { beratBadan = it; hasilDihitung = false },
                label = { Text("Berat Badan (kg)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            OutlinedTextField(
                value = tinggiBadan,
                onValueChange = { tinggiBadan = it; hasilDihitung = false },
                label = { Text("Tinggi Badan (cm)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true
            )

            // Button Hitung
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
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Icon(Icons.Default.Edit, null)
                Spacer(Modifier.width(8.dp))
                Text("Hitung Status Gizi")
            }

            if (hasilDihitung) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isNormal) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = statusGizi, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = if (isNormal) Color(0xFF2E7D32) else Color(0xFFC62828))
                        Text(text = if (isNormal) "Kebutuhan gizi tercukupi." else "Perlu pemantauan intensif!", fontSize = 14.sp)
                    }
                }

                Spacer(Modifier.height(8.dp))

                // Tombol Simpan
                if (isNormal) {
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
                            onNavigateBack()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                    ) {
                        Icon(Icons.Default.Check, null)
                        Spacer(Modifier.width(8.dp))
                        Text("Simpan Data & Selesai")
                    }
                } else {
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
                            onNavigateToJadwal()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828))
                    ) {
                        Icon(Icons.Default.DateRange, null)
                        Spacer(Modifier.width(8.dp))
                        Text("Buat Jadwal Kontrol Rutin")
                    }
                }
            }
        }
    }
}