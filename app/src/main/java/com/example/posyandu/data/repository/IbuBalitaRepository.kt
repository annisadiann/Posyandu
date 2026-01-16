package com.example.posyandu.data.repository

import com.example.posyandu.data.local.dao.*
import com.example.posyandu.data.local.entity.*
import com.example.posyandu.data.model.BalitaWithIbu
import com.example.posyandu.data.model.JadwalKontrolWithBalita
import com.example.posyandu.data.model.PemeriksaanWithBalita
import kotlinx.coroutines.flow.Flow

class IbuBalitaRepository(
    private val ibuBalitaDao: IbuBalitaDao,
    private val jadwalKontrolDao: JadwalKontrolDao,
    private val pemeriksaanDao: PemeriksaanDao,
    private val laporanDao: LaporanDao
) {
    // --- DATA PASIEN ---
    val allBalitaWithIbu: Flow<List<BalitaWithIbu>> = ibuBalitaDao.getBalitaWithIbu()

    suspend fun insertDataBaru(ibu: IbuBalita, balita: Balita) {
        val idIbuBaru = ibuBalitaDao.insertIbu(ibu)
        val balitaLengkap = balita.copy(id_ibu = idIbuBaru.toInt())
        ibuBalitaDao.insertBalita(balitaLengkap)
    }

    // UPDATE KEDUA TABEL
    suspend fun updateIbu(ibu: IbuBalita) = ibuBalitaDao.updateIbu(ibu)
    suspend fun updateBalita(balita: Balita) = ibuBalitaDao.updateBalita(balita)

    suspend fun delete(ibu: IbuBalita) = ibuBalitaDao.deleteIbu(ibu)

    // --- PEMERIKSAAN ---
    val allPemeriksaan: Flow<List<PemeriksaanWithBalita>> = pemeriksaanDao.getAllPemeriksaanWithBalita()
    val totalPemeriksaanAll: Flow<Int> = pemeriksaanDao.getTotalPemeriksaanAll()

    suspend fun insertPemeriksaan(pemeriksaan: Pemeriksaan) = pemeriksaanDao.insertPemeriksaan(pemeriksaan)

    // --- STATISTIK LAPORAN ---
    fun getTotalPemeriksaan(start: Long, end: Long): Flow<Int> = pemeriksaanDao.getTotalPemeriksaan(start, end)
    fun getGiziBaikCount(start: Long, end: Long): Flow<Int> = pemeriksaanDao.getGiziBaikCount(start, end)
    fun getPerluPerhatianCount(start: Long, end: Long): Flow<Int> = pemeriksaanDao.getPerluPerhatianCount(start, end)
    fun getJadwalTerpenuhiCount(start: Long, end: Long): Flow<Int> = jadwalKontrolDao.getJadwalTerpenuhiCount(start, end)

    // --- LAPORAN ---
    suspend fun simpanLaporan(laporan: Laporan) = laporanDao.simpanLaporan(laporan)

    // --- JADWAL ---
    val allJadwalWithBalita: Flow<List<JadwalKontrolWithBalita>> = jadwalKontrolDao.getAllJadwalWithBalita()
    suspend fun updateJadwal(jadwal: JadwalKontrol) = jadwalKontrolDao.updateJadwal(jadwal)
    suspend fun deleteJadwal(jadwal: JadwalKontrol) = jadwalKontrolDao.deleteJadwal(jadwal)
    suspend fun insertJadwal(jadwal: JadwalKontrol) = jadwalKontrolDao.insertJadwal(jadwal)
}