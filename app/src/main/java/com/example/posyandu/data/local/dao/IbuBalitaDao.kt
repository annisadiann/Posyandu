package com.example.posyandu.data.local.dao

import androidx.room.*
import com.example.posyandu.data.local.entity.Balita
import com.example.posyandu.data.local.entity.IbuBalita
import com.example.posyandu.data.local.entity.Pemeriksaan
import com.example.posyandu.data.model.BalitaWithIbu
import kotlinx.coroutines.flow.Flow

@Dao
interface IbuBalitaDao {
    // Mengambil data balita beserta info ibunya menggunakan relasi
    @Transaction
    @Query("SELECT * FROM balita")
    fun getBalitaWithIbu(): Flow<List<BalitaWithIbu>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIbu(ibu: IbuBalita): Long // Harus return Long untuk ID

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBalita(balita: Balita)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPemeriksaan(pemeriksaan: Pemeriksaan)

    @Update
    suspend fun updateIbu(ibu: IbuBalita)

    @Delete
    suspend fun deleteIbu(ibu: IbuBalita)
}