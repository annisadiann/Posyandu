package com.example.posyandu.ui.pemeriksaan

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.posyandu.data.model.BalitaWithIbu
import com.example.posyandu.viewmodel.IbuBalitaViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PemeriksaanScreen(
    viewModel: IbuBalitaViewModel,
    onNavigateBack: () -> Unit,
    onPatientSelected: (Int) -> Unit
) {
    val listPasien by viewModel.allIbu.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val filteredList = listPasien.filter {
        it.balita.nama_balita.contains(searchQuery, ignoreCase = true) ||
                it.ibu.nama_ibu.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pilih Pasien", color = Color.White, fontWeight = FontWeight.ExtraBold) },
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
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFFFF1493), Color(0xFFFF69B4))
                        ),
                        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                    )
                    .padding(bottom = 24.dp, start = 16.dp, end = 16.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Cari Nama Ibu atau Balita...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFFFF1493)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (filteredList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Person, null, Modifier.size(80.dp), Color.LightGray)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Pasien tidak ditemukan", color = Color.Gray)
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredList) { item ->
                        PatientItem(
                            data = item,
                            onClick = { onPatientSelected(item.balita.id_balita) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PatientItem(data: BalitaWithIbu, onClick: () -> Unit) {
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = Color(0xFFFFD1DC),
                modifier = Modifier.size(50.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = null,
                    modifier = Modifier.padding(12.dp),
                    tint = Color(0xFF880E4F)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = data.balita.nama_balita,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 17.sp,
                    color = Color(0xFF880E4F)
                )
                Text(
                    text = "Ibu: ${data.ibu.nama_ibu}",
                    fontSize = 13.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Lahir: ${dateFormat.format(data.balita.tanggal_lahir)}",
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }

            // Ikon penunjuk standar
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                tint = Color(0xFFFF69B4),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}