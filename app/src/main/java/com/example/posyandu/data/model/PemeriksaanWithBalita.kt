package com.example.posyandu.data.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.posyandu.data.local.entity.Pemeriksaan
import com.example.posyandu.data.local.entity.Balita

data class PemeriksaanWithBalita(
    @Embedded val pemeriksaan: Pemeriksaan,
    @Relation(
        parentColumn = "id_balita",
        entityColumn = "id_balita"
    )
    val balita: Balita
)