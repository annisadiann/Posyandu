package com.example.posyandu.ui.pemeriksaan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.posyandu.viewmodel.IbuBalitaViewModel

@Composable
fun DetailPemeriksaanScreen(
    pemeriksaanId: Int,
    viewModel: IbuBalitaViewModel, // Tambahkan ViewModel
    onFinish: () -> Unit
) {
    // Cari data pemeriksaan berdasarkan ID
    val allData by viewModel.allPemeriksaan.collectAsState()
    val data = allData.find { it.pemeriksaan.id_pemeriksaan == pemeriksaanId }

    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFFFFD1DC)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.padding(24.dp),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Icon berubah warna sesuai status gizi
                val isGiziBuruk = data?.pemeriksaan?.status_gizi?.contains("Buruk", true) == true

                Icon(
                    imageVector = if (isGiziBuruk) Icons.Default.Warning else Icons.Default.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = if (isGiziBuruk) Color.Red else Color(0xFF4CAF50)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Hasil Pemeriksaan untuk ${data?.balita?.nama_balita ?: "Balita"}", fontSize = 14.sp, color = Color.Gray)
                Text(
                    text = data?.pemeriksaan?.status_gizi?.uppercase() ?: "DATA TIDAK DITEMUKAN",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (isGiziBuruk) Color.Red else Color(0xFFFF1493)
                )

                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(color = Color(0xFFFFD1DC))
                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Text("Berat Badan:", color = Color.Gray)
                    Text("${data?.pemeriksaan?.berat_badan ?: 0.0} kg", fontWeight = FontWeight.Bold)
                }
                Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Text("Tinggi Badan:", color = Color.Gray)
                    Text("${data?.pemeriksaan?.tinggi_badan ?: 0.0} cm", fontWeight = FontWeight.Bold)
                }
                Row(modifier = Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Text("Umur:", color = Color.Gray)
                    Text("${data?.pemeriksaan?.umur_balita ?: 0} Bulan", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = onFinish,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF69B4))
                ) {
                    Text("KEMBALI KE DAFTAR")
                }
            }
        }
    }
}