package com.example.posyandu.data.local.dao

import androidx.room.*
import com.example.posyandu.data.local.entity.StandarAntropometri

@Dao
interface StandarAntropometriDao {
    @Query("""
        SELECT kategori FROM standar_antropometri 
        WHERE umur_bulan = :umur AND jenis_kelamin = :jk 
        AND :bb BETWEEN bb_min AND bb_max 
        AND :tb BETWEEN tb_min AND tb_max LIMIT 1
    """)
    suspend fun getStatusGizi(umur: Int, jk: String, bb: Double, tb: Double): String?
}