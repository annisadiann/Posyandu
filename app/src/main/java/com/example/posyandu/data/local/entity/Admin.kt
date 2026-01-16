package com.example.posyandu.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "admin")
data class Admin(
    @PrimaryKey(autoGenerate = true)
    val id_admin: Int = 0, // Kunci utama
    val username: String, // Varchar (50)
    val password: String, // Kata sandi plain text
    val nama: String // Nama lengkap admin
)