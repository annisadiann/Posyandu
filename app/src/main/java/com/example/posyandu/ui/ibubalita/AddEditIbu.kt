package com.example.posyandu.ui.ibubalita

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import com.example.posyandu.data.local.entity.Balita
import com.example.posyandu.data.local.entity.IbuBalita
import com.example.posyandu.viewmodel.IbuBalitaViewModel
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditIbuScreen(
    ibuId: Int,
    onNavigateBack: () -> Unit,
    viewModel: IbuBalitaViewModel
) {
    var namaIbu by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }
    var telepon by remember { mutableStateOf("") }
    var namaBalita by remember { mutableStateOf("") }
    var tglLahir by remember { mutableStateOf("") }
    var jenisKelamin by remember { mutableStateOf("Laki-laki") }

    var currentBalitaId by remember { mutableIntStateOf(0) }

    val isEditMode = ibuId != -1
    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale("id", "ID"))

    LaunchedEffect(key1 = ibuId) {
        if (isEditMode) {
            val dataExisting = viewModel.allIbu.value.find { it.ibu.id_ibu == ibuId }
            dataExisting?.let {
                namaIbu = it.ibu.nama_ibu
                alamat = it.ibu.alamat
                telepon = it.ibu.no_telp
                namaBalita = it.balita.nama_balita
                tglLahir = dateFormat.format(it.balita.tanggal_lahir)
                jenisKelamin = it.balita.jenis_kelamin
                currentBalitaId = it.balita.id_balita
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (isEditMode) "Edit Data Pasien" else "Tambah Pasien",
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold
                    )
                },
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
                .verticalScroll(rememberScrollState())
        ) {
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
                    text = if (isEditMode) "Perbarui informasi lengkap ibu dan balita." else "Lengkapi formulir untuk mendaftarkan pasien baru.",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 14.sp
                )
            }

            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // SEKSI IBU
                Text("INFORMASI IBU", fontWeight = FontWeight.Bold, color = Color(0xFF880E4F), fontSize = 14.sp)

                OutlinedTextField(
                    value = namaIbu,
                    onValueChange = { namaIbu = it },
                    label = { Text("Nama Lengkap Ibu") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Person, null, tint = Color(0xFFFF69B4)) },
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = alamat,
                    onValueChange = { alamat = it },
                    label = { Text("Alamat Tinggal") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Home, null, tint = Color(0xFFFF69B4)) },
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = telepon,
                    onValueChange = { telepon = it },
                    label = { Text("Nomor Telepon/WA") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Call, null, tint = Color(0xFFFF69B4)) },
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(8.dp))

                // SEKSI BALITA
                Text("INFORMASI BALITA", fontWeight = FontWeight.Bold, color = Color(0xFF880E4F), fontSize = 14.sp)

                OutlinedTextField(
                    value = namaBalita,
                    onValueChange = { namaBalita = it },
                    label = { Text("Nama Lengkap Balita") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Face, null, tint = Color(0xFFFF69B4)) },
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = tglLahir,
                    onValueChange = { tglLahir = it },
                    label = { Text("Tanggal Lahir (Tgl-Bln-Thn)") },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.DateRange, null, tint = Color(0xFFFF69B4)) },
                    placeholder = { Text("Contoh: 15-01-2024") },
                    shape = RoundedCornerShape(12.dp)
                )

                Column {
                    Text("Jenis Kelamin Balita", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.DarkGray)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = jenisKelamin == "Laki-laki",
                            onClick = { jenisKelamin = "Laki-laki" },
                            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFFF1493))
                        )
                        Text("Laki-laki")
                        Spacer(Modifier.width(20.dp))
                        RadioButton(
                            selected = jenisKelamin == "Perempuan",
                            onClick = { jenisKelamin = "Perempuan" },
                            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFFF1493))
                        )
                        Text("Perempuan")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (namaIbu.isNotBlank() && namaBalita.isNotBlank()) {
                            val dataIbu = IbuBalita(
                                id_ibu = if (isEditMode) ibuId else 0,
                                nama_ibu = namaIbu,
                                alamat = alamat,
                                no_telp = telepon
                            )

                            val dataBalita = Balita(
                                id_balita = currentBalitaId,
                                id_ibu = if (isEditMode) ibuId else 0,
                                nama_balita = namaBalita,
                                tanggal_lahir = try { dateFormat.parse(tglLahir) ?: Date() } catch (e: Exception) { Date() },
                                jenis_kelamin = jenisKelamin
                            )

                            if (isEditMode) {
                                viewModel.updateDataPasien(dataIbu, dataBalita)
                            } else {
                                viewModel.tambahPasienBaru(dataIbu, dataBalita)
                            }
                            onNavigateBack()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF1493)),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(4.dp)
                ) {
                    Icon(Icons.Default.Check, null)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = if (isEditMode) "SIMPAN PERUBAHAN" else "SIMPAN DATA PASIEN",
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 1.sp
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}