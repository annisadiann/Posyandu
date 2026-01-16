package com.example.posyandu.data.repository

import androidx.lifecycle.LiveData
import com.example.posyandu.data.local.dao.BalitaDao
import com.example.posyandu.data.local.entity.Balita
import kotlinx.coroutines.flow.Flow

class BalitaRepository(private val balitaDao: BalitaDao) {
    fun getBalitaByIbu(idIbu: Int): Flow<List<Balita>> = balitaDao.getBalitaByIbu(idIbu)

    suspend fun insert(balita: Balita) = balitaDao.insertBalita(balita)
    suspend fun update(balita: Balita) = balitaDao.updateBalita(balita)
    suspend fun delete(balita: Balita) = balitaDao.deleteBalita(balita)

    // Untuk pencarian balita saat akan melakukan pemeriksaan [cite: 120]
    suspend fun searchBalita(query: String) = balitaDao.searchBalita("%$query%")
}