package com.example.posyandu.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "balita",
    foreignKeys = [
        ForeignKey(
            entity = IbuBalita::class,
            parentColumns = ["id_ibu"],
            childColumns = ["id_ibu"],
            onDelete = ForeignKey.CASCADE // Jika ibu dihapus, data balita ikut terhapus
        )
    ]
)
data class Balita(
    @PrimaryKey(autoGenerate = true) val id_balita: Int = 0, // Kunci utama
    val id_ibu: Int, // Kunci asing
    val nama_balita: String, // Varchar (100)
    val tanggal_lahir: Date, // Date
    val jenis_kelamin: String // Varchar (15)
)