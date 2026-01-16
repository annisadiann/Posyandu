package com.example.posyandu.ui.balita

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.posyandu.data.local.entity.Balita
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun BalitaItem(
    balita: Balita,
    onEditClick: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale("id", "ID"))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFD1DC)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    tint = Color(0xFFFF1493)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = balita.nama_balita,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF1493)
                )
                Text(
                    text = "Lahir: ${dateFormat.format(balita.tanggal_lahir)}",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Jenis Kelamin: ${balita.jenis_kelamin}",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            TextButton(onClick = onEditClick) {
                Text("Edit", color = Color(0xFFFF69B4), fontWeight = FontWeight.Bold)
            }
        }
    }
}