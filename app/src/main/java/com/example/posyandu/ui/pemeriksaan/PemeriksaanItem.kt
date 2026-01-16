package com.example.posyandu.ui.pemeriksaan

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.posyandu.data.local.entity.Pemeriksaan
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun PemeriksaanItem(
    pemeriksaan: Pemeriksaan,
    onClick: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = dateFormat.format(pemeriksaan.tanggal_pemeriksaan),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF1493)
                )
                Text(
                    text = "Status: ${pemeriksaan.status_gizi}",
                    fontSize = 13.sp,
                    color = if (pemeriksaan.status_gizi == "Tercukupi") Color(0xFF4CAF50) else Color.Red
                )
            }

            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD1DC)),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text("Detail", color = Color(0xFFFF1493), fontSize = 12.sp)
            }
        }
    }
}