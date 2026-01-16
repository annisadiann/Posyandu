package com.example.posyandu.ui.jadwal

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.posyandu.data.local.entity.JadwalKontrol
import com.example.posyandu.viewmodel.IbuBalitaViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditJadwalScreen(
    jadwalId: Int,
    viewModel: IbuBalitaViewModel,
    onNavigateBack: () -> Unit
) {
    val listBalita by viewModel.allIbu.collectAsState()
    var selectedBalitaId by remember { mutableStateOf(-1) }
    var selectedDate by remember { mutableStateOf(Date()) }
    var showDatePicker by remember { mutableStateOf(false) }
    val isEditMode = jadwalId != -1
    val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))

    // Load data jika Edit Mode
    LaunchedEffect(jadwalId) {
        if (isEditMode) {
            val existing = viewModel.allJadwal.value.find { it.jadwal.id_jadwal == jadwalId }
            existing?.let {
                selectedBalitaId = it.jadwal.id_balita
                selectedDate = it.jadwal.tanggal_kontrol
            }
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDate.time)
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { selectedDate = Date(it) }
                    showDatePicker = false
                }) { Text("OK") }
            }
        ) { DatePicker(state = datePickerState) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Edit Jadwal" else "Tambah Jadwal", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Kembali", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFFF69B4))
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp).fillMaxSize()) {
            Text("Pilih Balita", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            // Pilihan Balita (Sederhana)
            Card(elevation = CardDefaults.cardElevation(2.dp)) {
                Column(modifier = Modifier.padding(8.dp)) {
                    listBalita.forEach { item ->
                        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                            RadioButton(
                                selected = selectedBalitaId == item.balita.id_balita,
                                onClick = { selectedBalitaId = item.balita.id_balita }
                            )
                            Text(item.balita.nama_balita)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("Tanggal Kontrol", fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = dateFormatter.format(selectedDate),
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Pilih Tanggal")
                    }
                }
            )

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    if (selectedBalitaId != -1) {
                        val jadwal = JadwalKontrol(
                            id_jadwal = if (isEditMode) jadwalId else 0,
                            id_balita = selectedBalitaId,
                            id_admin = 1, // ID Admin default
                            tanggal_kontrol = selectedDate,
                            sudah_dihubungi = false
                        )
                        if (isEditMode) viewModel.updateJadwal(jadwal) else viewModel.tambahJadwal(jadwal)
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF69B4))
            ) {
                Icon(Icons.Default.Check, null)
                Spacer(Modifier.width(8.dp))
                Text("Simpan Jadwal")
            }
        }
    }
}