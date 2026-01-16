package com.example.posyandu.data.local.dao

import androidx.room.*
import com.example.posyandu.data.local.entity.Balita
import com.example.posyandu.data.local.entity.IbuBalita
import com.example.posyandu.data.local.entity.Pemeriksaan
import com.example.posyandu.data.model.BalitaWithIbu
import kotlinx.coroutines.flow.Flow

@Dao
interface IbuBalitaDao {
    @Transaction
    @Query("SELECT * FROM balita")
    fun getBalitaWithIbu(): Flow<List<BalitaWithIbu>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIbu(ibu: IbuBalita): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBalita(balita: Balita)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPemeriksaan(pemeriksaan: Pemeriksaan)

    @Update
    suspend fun updateIbu(ibu: IbuBalita)

    @Update
    suspend fun updateBalita(balita: Balita)

    @Delete
    suspend fun deleteIbu(ibu: IbuBalita)

    @Query("SELECT * FROM balita WHERE id_balita = :id")
    suspend fun getBalitaById(id: Int): Balita?
}