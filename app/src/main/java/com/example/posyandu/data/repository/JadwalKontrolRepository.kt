package com.example.posyandu.data.repository

import com.example.posyandu.data.local.dao.JadwalKontrolDao
import com.example.posyandu.data.local.entity.JadwalKontrol
import com.example.posyandu.data.model.JadwalKontrolWithBalita
import kotlinx.coroutines.flow.Flow

class JadwalKontrolRepository(private val jadwalDao: JadwalKontrolDao) {

    val allJadwal: Flow<List<JadwalKontrolWithBalita>> = jadwalDao.getAllJadwalWithBalita()

    suspend fun insert(jadwal: JadwalKontrol) = jadwalDao.insertJadwal(jadwal)

    suspend fun update(jadwal: JadwalKontrol) = jadwalDao.updateJadwal(jadwal)

    suspend fun delete(jadwal: JadwalKontrol) = jadwalDao.deleteJadwal(jadwal)
}