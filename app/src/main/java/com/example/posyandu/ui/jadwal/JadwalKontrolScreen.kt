package com.example.posyandu.ui.jadwal

import androidx.compose.foundation.background
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
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
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
                title = { Text("Agenda Kontrol", color = Color.White, fontWeight = FontWeight.ExtraBold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Kembali", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFF1493))
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddJadwal,
                containerColor = Color(0xFFFF1493),
                contentColor = Color.White,
                shape = CircleShape
            ) { Icon(Icons.Default.Add, "Tambah", modifier = Modifier.size(30.dp)) }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFFFFAFA))
        ) {
            // Header Info dengan Gradasi Tipis
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFFFF1493), Color(0xFFFF69B4))
                        ),
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    )
                    .padding(20.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.2f)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Notifications, null, tint = Color.White)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Pantau dan ingatkan jadwal kunjungan rutin balita.",
                            fontSize = 13.sp, color = Color.White, lineHeight = 18.sp
                        )
                    }
                }
            }

            if (daftarJadwal.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.DateRange, null, Modifier.size(80.dp), Color.LightGray)
                        Text("Belum ada agenda kontrol", color = Color.Gray)
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
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
    val isDone = item.jadwal.sudah_dihubungi

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDone) Color(0xFFF1F1F1) else Color.White
        ),
        elevation = CardDefaults.cardElevation(if (isDone) 0.dp else 4.dp),
        border = if (isDone) null else androidx.compose.foundation.BorderStroke(0.5.dp, Color.LightGray.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar Ikon
            Surface(
                shape = CircleShape,
                color = if (isDone) Color.LightGray else Color(0xFFFFD1DC),
                modifier = Modifier.size(44.dp)
            ) {
                Icon(
                    imageVector = if (isDone) Icons.Default.CheckCircle else Icons.Default.Face,
                    contentDescription = null,
                    tint = if (isDone) Color.Gray else Color(0xFF880E4F),
                    modifier = Modifier.padding(10.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.balita.nama_balita,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (isDone) Color.Gray else Color(0xFF880E4F),
                    fontSize = 17.sp
                )
                Text(
                    text = "Tgl Kontrol: ${dateFormat.format(item.jadwal.tanggal_kontrol)}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            // Aksi
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (!isDone) {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, "Edit", tint = Color(0xFF1976D2), modifier = Modifier.size(20.dp))
                    }
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, "Hapus", tint = Color(0xFFD32F2F), modifier = Modifier.size(20.dp))
                }

                VerticalDivider(modifier = Modifier.height(30.dp).padding(horizontal = 4.dp))

                Checkbox(
                    checked = isDone,
                    onCheckedChange = onStatusChange,
                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF4CAF50))
                )
            }
        }
    }
}