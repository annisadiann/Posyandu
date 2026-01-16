package com.example.posyandu.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "laporan",
    foreignKeys = [
        ForeignKey(entity = Admin::class, parentColumns = ["id_admin"], childColumns = ["id_admin"])
    ]
)
data class Laporan(
    @PrimaryKey(autoGenerate = true) val id_laporan: Int = 0, // Kunci utama
    val id_admin: Int, // Admin yang membuat laporan
    val jenis_laporan: String, // Tipe laporan
    val periode: Date, // Periode laporan
    val isi_laporan: String, // TEXT: Konten rangkuman
    val created_at: Date = Date() // Waktu pembuatan otomatis
)