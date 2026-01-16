package com.example.posyandu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.posyandu.data.local.entity.JadwalKontrol
import com.example.posyandu.data.repository.JadwalKontrolRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class JadwalKontrolViewModel(private val repository: JadwalKontrolRepository) : ViewModel() {

    val allJadwal = repository.allJadwal
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Fungsi update menggunakan objek JadwalKontrol
    fun updateCeklist(jadwal: JadwalKontrol) = viewModelScope.launch {
        repository.update(jadwal)
    }

    fun deleteJadwal(jadwal: JadwalKontrol) = viewModelScope.launch {
        repository.delete(jadwal)
    }
}