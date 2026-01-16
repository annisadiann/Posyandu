package com.example.posyandu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.posyandu.data.local.entity.*
import com.example.posyandu.data.model.*
import com.example.posyandu.data.repository.IbuBalitaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class IbuBalitaViewModel(private val repository: IbuBalitaRepository) : ViewModel() {

    // --- STATE FILTER ---
    private val _startDate = MutableStateFlow(0L)
    private val _endDate = MutableStateFlow(Long.MAX_VALUE)
    private val _namaPeriode = MutableStateFlow("Semua Waktu")
    val namaPeriode: StateFlow<String> = _namaPeriode

    fun setFilterPeriode(start: Long, end: Long, label: String) {
        _startDate.value = start
        _endDate.value = end
        _namaPeriode.value = label
    }

    // --- DASHBOARD ---
    val statsDashboard: StateFlow<Int> = repository.totalPemeriksaanAll
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // --- DATA PASIEN ---
    val allIbu: StateFlow<List<BalitaWithIbu>> = repository.allBalitaWithIbu
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun tambahPasienBaru(ibu: IbuBalita, balita: Balita) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertDataBaru(ibu, balita)
    }

    fun updateDataPasien(ibu: IbuBalita, balita: Balita) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateIbu(ibu)
        repository.updateBalita(balita)
    }

    fun deleteIbu(ibu: IbuBalita) = viewModelScope.launch(Dispatchers.IO) { repository.delete(ibu) }

    // --- FITUR PEMERIKSAAN ---
    val allPemeriksaan: StateFlow<List<PemeriksaanWithBalita>> = repository.allPemeriksaan
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insertPemeriksaan(pemeriksaan: Pemeriksaan) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertPemeriksaan(pemeriksaan)
    }

    // --- JADWAL ---
    val allJadwal: StateFlow<List<JadwalKontrolWithBalita>> = repository.allJadwalWithBalita
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun tambahJadwal(jadwal: JadwalKontrol) = viewModelScope.launch(Dispatchers.IO) { repository.insertJadwal(jadwal) }
    fun updateJadwal(jadwal: JadwalKontrol) = viewModelScope.launch(Dispatchers.IO) { repository.updateJadwal(jadwal) }
    fun deleteJadwal(jadwal: JadwalKontrol) = viewModelScope.launch(Dispatchers.IO) { repository.deleteJadwal(jadwal) }

    // --- STATISTIK DINAMIS (LAPORAN) ---
    @OptIn(ExperimentalCoroutinesApi::class)
    val statsTotal: StateFlow<Int> = combine(_startDate, _endDate) { s, e -> Pair(s, e) }
        .flatMapLatest { (s, e) -> repository.getTotalPemeriksaan(s, e) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val statsGiziBaik: StateFlow<Int> = combine(_startDate, _endDate) { s, e -> Pair(s, e) }
        .flatMapLatest { (s, e) -> repository.getGiziBaikCount(s, e) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val statsPerhatian: StateFlow<Int> = combine(_startDate, _endDate) { s, e -> Pair(s, e) }
        .flatMapLatest { (s, e) -> repository.getPerluPerhatianCount(s, e) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val statsJadwal: StateFlow<Int> = combine(_startDate, _endDate) { s, e -> Pair(s, e) }
        .flatMapLatest { (s, e) -> repository.getJadwalTerpenuhiCount(s, e) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun simpanLaporanKeDatabase(total: Int, baik: Int, perhatian: Int, jadwal: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            val laporanBaru = Laporan(
                id_admin = 1,
                jenis_laporan = "Rekapitulasi",
                periode = Date(),
                isi_laporan = "Rekap Periode: ${_namaPeriode.value} -> Total=$total, Baik=$baik, Perhatian=$perhatian",
                created_at = Date()
            )
            repository.simpanLaporan(laporanBaru)
        }
}