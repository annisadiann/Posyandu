package com.example.posyandu.utils

object ValidationHelper {

    // Validasi input pemeriksaan BB/TB agar tidak terjadi kesalahan data
    fun isInputPemeriksaanValid(bb: String, tb: String): Boolean {
        val berat = bb.toDoubleOrNull()
        val tinggi = tb.toDoubleOrNull()
        return berat != null && tinggi != null && berat > 0 && tinggi > 0
    }

    // Validasi registrasi admin (REQ-REG) [cite: 173]
    fun isRegisterValid(username: String, pass: String, nama: String): Boolean {
        // Gunakan .isNotBlank() untuk memastikan tidak hanya spasi
        return username.isNotBlank() && pass.isNotBlank() && nama.isNotBlank()
    }
}