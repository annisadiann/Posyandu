package com.example.posyandu.data.local.dao

import androidx.room.*
import com.example.posyandu.data.local.entity.Admin

@Dao
interface AdminDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun register(admin: Admin) // REQ-REG-3

    @Query("SELECT * FROM admin WHERE username = :username LIMIT 1")
    suspend fun login(username: String): Admin? // REQ-LOG-2

    @Query("SELECT * FROM admin WHERE id_admin = :id LIMIT 1")
    suspend fun getAdminById(id: Int): Admin?

    @Query("SELECT COUNT(*) FROM admin")
    suspend fun getAdminCount(): Int
}