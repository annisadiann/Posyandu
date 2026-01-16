package com.example.posyandu.data.local.dao

import androidx.room.*
import com.example.posyandu.data.local.entity.Laporan
import kotlinx.coroutines.flow.Flow

@Dao
interface LaporanDao {
    @Insert
    suspend fun simpanLaporan(laporan: Laporan)

    //Ambil data sesuai periode
    @Query("SELECT * FROM laporan WHERE periode BETWEEN :start AND :end")
    suspend fun getLaporanByPeriode(start: Long, end: Long): List<Laporan>

    @Query("SELECT * FROM laporan ORDER BY created_at DESC")
    fun getAllLaporan(): Flow<List<Laporan>>
}
