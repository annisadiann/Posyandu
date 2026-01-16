package com.example.posyandu.ui.ibubalita

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
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IbuBalitaScreen(
    viewModel: IbuBalitaViewModel,
    onNavigateBack: () -> Unit,
    onNavigateToAddIbu: () -> Unit,
    onNavigateToEditIbu: (Int) -> Unit
) {
    val listIbu by viewModel.allIbu.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var itemToDelete by remember { mutableStateOf<BalitaWithIbu?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Hapus Data", fontWeight = FontWeight.Bold) },
            text = {
                Text("Apakah Anda yakin ingin menghapus data Ibu ${itemToDelete?.ibu?.nama_ibu} dan Balita ${itemToDelete?.balita?.nama_balita}?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        itemToDelete?.let { data ->
                            viewModel.deleteIbu(data.ibu)
                            scope.launch {
                                snackbarHostState.showSnackbar("Data berhasil dihapus")
                            }
                        }
                        showDeleteDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                ) {
                    Text("Ya, Hapus", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Daftar Pasien", color = Color.White, fontWeight = FontWeight.ExtraBold) },
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
                onClick = onNavigateToAddIbu,
                containerColor = Color(0xFFFF1493),
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, "Tambah", modifier = Modifier.size(30.dp))
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFFFFAFA))
        ) {
            // Header Info Kecil
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFF1493).copy(alpha = 0.05f))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Total Pasien Terdaftar: ${listIbu.size}",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color(0xFF880E4F)
                )
            }

            if (listIbu.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.Person, null, Modifier.size(80.dp), Color.LightGray)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Data masih kosong", color = Color.Gray)
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(listIbu) { data ->
                        IbuBalitaItemRow(
                            data = data,
                            onEditClick = { onNavigateToEditIbu(data.ibu.id_ibu) },
                            onDeleteClick = {
                                itemToDelete = data
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
fun IbuBalitaItemRow(
    data: BalitaWithIbu,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color(0xFFFFD1DC),
                    modifier = Modifier.size(45.dp)
                ) {
                    Icon(
                        Icons.Default.Face,
                        null,
                        tint = Color(0xFF880E4F),
                        modifier = Modifier.padding(10.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = data.balita.nama_balita,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 18.sp,
                        color = Color(0xFF880E4F)
                    )
                    Text(
                        text = "Ibu: ${data.ibu.nama_ibu}",
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                }

                Row {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFE3F2FD),
                        modifier = Modifier.size(36.dp).clickable { onEditClick() }
                    ) {
                        Icon(Icons.Default.Edit, "Edit", tint = Color(0xFF1976D2), modifier = Modifier.padding(8.dp))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFFFEBEE),
                        modifier = Modifier.size(36.dp).clickable { onDeleteClick() }
                    ) {
                        Icon(Icons.Default.Delete, "Hapus", tint = Color(0xFFD32F2F), modifier = Modifier.padding(8.dp))
                    }
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), thickness = 0.5.dp, color = Color.LightGray)

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                InfoIconText(icon = Icons.Default.DateRange, text = dateFormat.format(data.balita.tanggal_lahir))
                InfoIconText(icon = Icons.Default.LocationOn, text = data.ibu.alamat)
            }
            Spacer(modifier = Modifier.height(4.dp))
            InfoIconText(icon = Icons.Default.Phone, text = data.ibu.no_telp)
        }
    }
}

@Composable
fun InfoIconText(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, modifier = Modifier.size(14.dp), tint = Color.Gray)
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = text, fontSize = 12.sp, color = Color.Gray)
    }
}