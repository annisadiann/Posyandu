package com.example.posyandu

import android.app.Application
import com.example.posyandu.data.local.database.PosyanduDatabase
import com.example.posyandu.data.repository.*

class PosyanduApplication : Application() {

    // Inisialisasi Database secara lazy
    private val database by lazy { PosyanduDatabase.getDatabase(this) }

    // Repository Admin
    val adminRepository by lazy { AdminRepository(database.adminDao()) }

    // PERBAIKAN: Masukkan 4 DAO agar sinkron dengan constructor IbuBalitaRepository terbaru
    val ibuBalitaRepository by lazy {
        IbuBalitaRepository(
            database.ibuBalitaDao(),
            database.jadwalKontrolDao(),
            database.pemeriksaanDao(),
            database.laporanDao() // TAMBAHKAN INI agar tidak error lagi
        )
    }

    val balitaRepository by lazy { BalitaRepository(database.balitaDao()) }

    val pemeriksaanRepository by lazy {
        PemeriksaanRepository(database.pemeriksaanDao(), database.standarAntropometriDao())
    }

    val jadwalKontrolRepository by lazy { JadwalKontrolRepository(database.jadwalKontrolDao()) }

    val laporanRepository by lazy { LaporanRepository(database.laporanDao()) }

    override fun onCreate() {
        super.onCreate()
    }
}