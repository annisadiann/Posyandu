package com.example.posyandu.data.local.dao

import androidx.room.*
import com.example.posyandu.data.local.entity.JadwalKontrol
import com.example.posyandu.data.model.JadwalKontrolWithBalita
import kotlinx.coroutines.flow.Flow

@Dao
interface JadwalKontrolDao {
    @Transaction
    @Query("SELECT * FROM jadwal_kontrol ORDER BY tanggal_kontrol ASC")
    fun getAllJadwalWithBalita(): Flow<List<JadwalKontrolWithBalita>>

    @Query("""
        SELECT COUNT(*) FROM jadwal_kontrol 
        WHERE sudah_dihubungi = 1 
        AND tanggal_kontrol >= :start AND tanggal_kontrol <= :end
    """)
    fun getJadwalTerpenuhiCount(start: Long, end: Long): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJadwal(jadwal: JadwalKontrol)

    @Update
    suspend fun updateJadwal(jadwal: JadwalKontrol)

    @Delete
    suspend fun deleteJadwal(jadwal: JadwalKontrol)
}