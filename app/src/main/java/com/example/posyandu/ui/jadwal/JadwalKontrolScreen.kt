package com.example.posyandu.ui.jadwal

import androidx.compose.foundation.background
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
import com.example.posyandu.data.model.JadwalKontrolWithBalita
import com.example.posyandu.viewmodel.IbuBalitaViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JadwalKontrolScreen(
    viewModel: IbuBalitaViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToAddJadwal: () -> Unit,
    onNavigateToEditJadwal: (Int) -> Unit
) {
    val daftarJadwal by viewModel.allJadwal.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var itemToDelete by remember { mutableStateOf<JadwalKontrolWithBalita?>(null) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Hapus Jadwal", fontWeight = FontWeight.Bold) },
            text = { Text("Yakin ingin menghapus jadwal untuk ${itemToDelete?.balita?.nama_balita}?") },
            confirmButton = {
                Button(
                    onClick = {
                        itemToDelete?.let {
                            viewModel.deleteJadwal(it.jadwal)
                            scope.launch { snackbarHostState.showSnackbar("Jadwal berhasil dihapus") }
                        }
                        showDeleteDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) { Text("Hapus", color = Color.White) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Batal") }
            }
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Jadwal Kontrol", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Kembali", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFF69B4))
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddJadwal,
                containerColor = Color(0xFFFF69B4),
                contentColor = Color.White
            ) { Icon(Icons.Default.Add, "Tambah") }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFFFFAFA))
        ) {
            Card(
                modifier = Modifier.padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFD1DC)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Notifications, null, tint = Color(0xFFFF1493))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Ingatkan orang tua balita untuk kontrol rutin tepat waktu.",
                        fontSize = 13.sp, color = Color(0xFF880E4F), lineHeight = 18.sp
                    )
                }
            }

            if (daftarJadwal.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Belum ada data jadwal", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(daftarJadwal) { item ->
                        JadwalItem(
                            item = item,
                            onStatusChange = { checked ->
                                viewModel.updateJadwal(item.jadwal.copy(sudah_dihubungi = checked))
                            },
                            onEdit = { onNavigateToEditJadwal(item.jadwal.id_jadwal) },
                            onDelete = {
                                itemToDelete = item
                                showDeleteDialog = true
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun JadwalItem(
    item: JadwalKontrolWithBalita,
    onStatusChange: (Boolean) -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.balita.nama_balita,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF1493),
                    fontSize = 17.sp
                )
                Text(
                    text = "Kontrol: ${dateFormat.format(item.jadwal.tanggal_kontrol)}",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, "Edit", tint = Color.Blue, modifier = Modifier.size(20.dp))
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, "Hapus", tint = Color.Red, modifier = Modifier.size(20.dp))
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Checkbox(
                        checked = item.jadwal.sudah_dihubungi,
                        onCheckedChange = onStatusChange,
                        colors = CheckboxDefaults.colors(checkedColor = Color(0xFFFF69B4))
                    )
                    Text(
                        text = if (item.jadwal.sudah_dihubungi) "Sudah" else "Hubungi",
                        fontSize = 9.sp,
                        color = if (item.jadwal.sudah_dihubungi) Color(0xFF4CAF50) else Color.Gray
                    )
                }
            }
        }
    }
}