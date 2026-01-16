package com.example.posyandu.data.local.dao

import androidx.room.*
import com.example.posyandu.data.local.entity.Pemeriksaan
import com.example.posyandu.data.model.PemeriksaanWithBalita
import kotlinx.coroutines.flow.Flow

@Dao
interface PemeriksaanDao {
    @Insert
    suspend fun insertPemeriksaan(pemeriksaan: Pemeriksaan)

    @Query("SELECT * FROM pemeriksaan WHERE id_balita = :idBalita ORDER BY tanggal_pemeriksaan DESC")
    fun getRiwayatPemeriksaan(idBalita: Int): Flow<List<Pemeriksaan>>

    @Transaction
    @Query("SELECT * FROM pemeriksaan ORDER BY tanggal_pemeriksaan DESC")
    fun getAllPemeriksaanWithBalita(): Flow<List<PemeriksaanWithBalita>>

    @Query("SELECT COUNT(*) FROM pemeriksaan")
    fun getTotalPemeriksaanAll(): Flow<Int>

    // Filter Tanggal
    @Query("""
        SELECT COUNT(*) FROM pemeriksaan 
        WHERE tanggal_pemeriksaan >= :start AND tanggal_pemeriksaan <= :end
    """)
    fun getTotalPemeriksaan(start: Long, end: Long): Flow<Int>

    @Query("""
        SELECT COUNT(*) FROM pemeriksaan 
        WHERE (status_gizi LIKE '%Baik%' OR status_gizi LIKE '%Normal%')
        AND tanggal_pemeriksaan >= :start AND tanggal_pemeriksaan <= :end
    """)
    fun getGiziBaikCount(start: Long, end: Long): Flow<Int>

    @Query("""
        SELECT COUNT(*) FROM pemeriksaan 
        WHERE (status_gizi LIKE '%Buruk%' OR status_gizi LIKE '%Stunting%' OR status_gizi LIKE '%Kurang%')
        AND tanggal_pemeriksaan >= :start AND tanggal_pemeriksaan <= :end
    """)
    fun getPerluPerhatianCount(start: Long, end: Long): Flow<Int>

    @Update
    suspend fun updatePemeriksaan(pemeriksaan: Pemeriksaan)

    @Query("DELETE FROM pemeriksaan WHERE id_pemeriksaan = :id")
    suspend fun deletePemeriksaan(id: Int)
}