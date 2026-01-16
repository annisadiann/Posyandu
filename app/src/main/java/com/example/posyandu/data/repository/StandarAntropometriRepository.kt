package com.example.posyandu.data.repository

import com.example.posyandu.data.local.dao.StandarAntropometriDao
import com.example.posyandu.data.local.entity.StandarAntropometri

class StandarAntropometriRepository(private val standarDao: StandarAntropometriDao) {
    class StandarAntropometriRepository(private val standarDao: StandarAntropometriDao) {
        // Memungkinkan update standar tanpa mengubah kode (Adaptability)
        suspend fun getStatus(umur: Int, jk: String, bb: Double, tb: Double) =
            standarDao.getStatusGizi(umur, jk, bb, tb)
    }
}
