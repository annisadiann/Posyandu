package com.example.posyandu.data.local.dao

import androidx.room.*
import com.example.posyandu.data.local.entity.Balita
import com.example.posyandu.data.local.entity.IbuBalita
import com.example.posyandu.data.local.entity.Pemeriksaan
import com.example.posyandu.data.model.BalitaWithIbu
import kotlinx.coroutines.flow.Flow

@Dao
interface IbuBalitaDao {

    // --- QUERY RELASI ---
    @Transaction
    @Query("SELECT * FROM balita")
    fun getBalitaWithIbu(): Flow<List<BalitaWithIbu>>

    // --- OPERASI IBU ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIbu(ibu: IbuBalita): Long

    @Update
    suspend fun updateIbu(ibu: IbuBalita)

    @Delete
    suspend fun deleteIbu(ibu: IbuBalita)

    // --- OPERASI BALITA (Pindahan dari BalitaDao) ---
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBalita(balita: Balita)

    @Update
    suspend fun updateBalita(balita: Balita)

    @Delete
    suspend fun deleteBalita(balita: Balita) // bisa hapus anak saja

    @Query("SELECT * FROM balita WHERE id_balita = :id")
    suspend fun getBalitaById(id: Int): Balita?

    // --- OPERASI PEMERIKSAAN
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPemeriksaan(pemeriksaan: Pemeriksaan)
}