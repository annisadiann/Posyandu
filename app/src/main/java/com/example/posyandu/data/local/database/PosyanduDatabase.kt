package com.example.posyandu.data.local.database

import com.example.posyandu.data.local.dao.JadwalKontrolDao
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.posyandu.data.local.dao.*
import com.example.posyandu.data.local.entity.*
import com.example.posyandu.data.local.DateConverter // Pastikan path ini sesuai dengan file DateConverter yang kamu buat

@Database(
    entities = [
        Admin::class,
        IbuBalita::class,
        Balita::class,
        Pemeriksaan::class,
        StandarAntropometri::class,
        JadwalKontrol::class,
        Laporan::class
    ],
    version = 8,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class PosyanduDatabase : RoomDatabase() {

    // Menghubungkan DAO untuk akses data
    abstract fun adminDao(): AdminDao
    abstract fun ibuBalitaDao(): IbuBalitaDao
    abstract fun balitaDao(): BalitaDao
    abstract fun pemeriksaanDao(): PemeriksaanDao
    abstract fun standarAntropometriDao(): StandarAntropometriDao
    abstract fun jadwalKontrolDao(): JadwalKontrolDao
    abstract fun laporanDao(): LaporanDao

    companion object {
        @Volatile
        private var INSTANCE: PosyanduDatabase? = null

        fun getDatabase(context: Context): PosyanduDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PosyanduDatabase::class.java,
                    "posyandu_database"
                )
                    // fallbackToDestructiveMigration akan menghapus data lama jika ada perubahan versi database
                    // Sangat berguna selama masa pengembangan agar tidak error 'Migration failed'
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}