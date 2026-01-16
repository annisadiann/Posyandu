package com.example.posyandu.ui.jadwal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
    var selectedBalitaId by remember { mutableIntStateOf(-1) }
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
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedDate.time
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { selectedDate = Date(it) }
                    showDatePicker = false
                }) { Text("Pilih", color = Color(0xFFFF1493), fontWeight = FontWeight.Bold) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Batal") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Edit Agenda" else "Atur Jadwal Baru", color = Color.White, fontWeight = FontWeight.ExtraBold) },
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
            // Header Cantik
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFFFF1493), Color(0xFFFF69B4))
                        ),
                        shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                    )
                    .padding(24.dp)
            ) {
                Text(
                    "Tentukan tanggal kunjungan rutin untuk balita pilihan Anda.",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }

            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
                    .weight(1f)
            ) {
                Text("Pilih Balita", fontWeight = FontWeight.Bold, color = Color(0xFF880E4F))
                Spacer(modifier = Modifier.height(12.dp))

                // List Balita
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        if (listBalita.isEmpty()) {
                            Text("Data balita kosong", modifier = Modifier.padding(16.dp), color = Color.Gray)
                        } else {
                            listBalita.forEach { item ->
                                val isSelected = selectedBalitaId == item.balita.id_balita
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { selectedBalitaId = item.balita.id_balita }
                                        .background(
                                            if (isSelected) Color(0xFFFFD1DC).copy(alpha = 0.3f) else Color.Transparent,
                                            RoundedCornerShape(8.dp)
                                        )
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = if (isSelected) Icons.Default.Face else Icons.Default.Person,
                                        contentDescription = null,
                                        tint = if (isSelected) Color(0xFFFF1493) else Color.Gray,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = item.balita.nama_balita,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) Color(0xFF880E4F) else Color.Black
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    RadioButton(
                                        selected = isSelected,
                                        onClick = { selectedBalitaId = item.balita.id_balita },
                                        colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFFF1493))
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text("Tanggal Kontrol", fontWeight = FontWeight.Bold, color = Color(0xFF880E4F))
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = dateFormatter.format(selectedDate),
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = { Icon(Icons.Default.DateRange, null, tint = Color(0xFFFF1493)) },
                    trailingIcon = {
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(Icons.Default.Edit, "Pilih Tanggal", tint = Color.Gray)
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFFF1493),
                        unfocusedBorderColor = Color.LightGray
                    )
                )
            }

            // Tombol Simpan
            Box(modifier = Modifier.padding(20.dp)) {
                Button(
                    onClick = {
                        if (selectedBalitaId != -1) {
                            val jadwal = JadwalKontrol(
                                id_jadwal = if (isEditMode) jadwalId else 0,
                                id_balita = selectedBalitaId,
                                id_admin = 1,
                                tanggal_kontrol = selectedDate,
                                sudah_dihubungi = false
                            )
                            if (isEditMode) viewModel.updateJadwal(jadwal) else viewModel.tambahJadwal(jadwal)
                            onNavigateBack()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF1493)),
                    enabled = selectedBalitaId != -1
                ) {
                    Icon(Icons.Default.Check, null)
                    Spacer(Modifier.width(8.dp))
                    Text("SIMPAN JADWAL", fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp)
                }
            }
        }
    }
}