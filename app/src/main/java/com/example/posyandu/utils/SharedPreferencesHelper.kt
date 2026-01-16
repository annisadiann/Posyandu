package com.example.posyandu.utils

import android.content.Context

class SharedPreferencesHelper(context: Context) {
    private val prefs = context.getSharedPreferences("posyandu_prefs", Context.MODE_PRIVATE)

    // Menyimpan status login sesuai REQ-LOG-6
    fun setLoginStatus(isLoggedIn: Boolean, adminId: Int) {
        prefs.edit().apply {
            putBoolean("is_logged_in", isLoggedIn)
            putInt("admin_id", adminId)
            apply()
        }
    }

    // Menghapus session saat logout sesuai REQ-LOGOUT-2 [cite: 265]
    fun clearSession() {
        prefs.edit().clear().apply()
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean("is_logged_in", false)
}