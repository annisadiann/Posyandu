package com.example.posyandu.data.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.posyandu.data.local.entity.JadwalKontrol
import com.example.posyandu.data.local.entity.Balita
import java.text.SimpleDateFormat
import java.util.*

data class JadwalKontrolWithBalita(
    @Embedded val jadwal: JadwalKontrol,
    @Relation(
        parentColumn = "id_balita",
        entityColumn = "id_balita"
    )
    val balita: Balita
)