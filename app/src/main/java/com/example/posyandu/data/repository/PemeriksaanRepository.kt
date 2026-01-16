package com.example.posyandu.data.repository

import com.example.posyandu.data.local.dao.PemeriksaanDao
import com.example.posyandu.data.local.dao.StandarAntropometriDao
import com.example.posyandu.data.local.entity.Pemeriksaan
import kotlinx.coroutines.flow.Flow

class PemeriksaanRepository(
    private val pemeriksaanDao: PemeriksaanDao,
    private val standarDao: StandarAntropometriDao
) {
    // Menyimpan hasil pemeriksaan
    suspend fun insertPemeriksaan(pemeriksaan: Pemeriksaan) =
        pemeriksaanDao.insertPemeriksaan(pemeriksaan)

    fun getRiwayat(idBalita: Int): Flow<List<Pemeriksaan>> =
        pemeriksaanDao.getRiwayatPemeriksaan(idBalita)

    // Logika perhitungan otomatis gizi
    suspend fun hitungStatusGizi(umur: Int, jk: String, bb: Double, tb: Double): String {
        return standarDao.getStatusGizi(umur, jk, bb, tb) ?: "Data Standar Tidak Ditemukan"
    }
}