package com.example.posyandu.ui.pemeriksaan

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
    // State mengambil data BalitaWithIbu dari ViewModel
    val listPasien by viewModel.allIbu.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    // Filter berdasarkan nama balita atau nama ibu
    val filteredList = listPasien.filter {
        it.balita.nama_balita.contains(searchQuery, ignoreCase = true) ||
                it.ibu.nama_ibu.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pilih Pasien", color = Color.White, fontWeight = FontWeight.Bold) },
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
                .background(Color(0xFFFFFAFA))
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Cari Nama Ibu atau Balita...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            if (filteredList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Person, null, Modifier.size(64.dp), Color.LightGray)
                        Text("Pasien tidak ditemukan", color = Color.Gray)
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(filteredList) { item ->
                        PatientItem(
                            data = item,
                            // Mengirim id_balita untuk input pemeriksaan
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
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = data.balita.nama_balita,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFFFF69B4)
                )
                Text(
                    text = "Ibu: ${data.ibu.nama_ibu}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Text(
                    text = "Lahir: ${dateFormat.format(data.balita.tanggal_lahir)}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.LightGray
            )
        }
    }
}