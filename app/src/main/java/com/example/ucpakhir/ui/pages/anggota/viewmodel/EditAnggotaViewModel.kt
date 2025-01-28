package com.example.ucpakhir.ui.pages.anggota.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucpakhir.model.Anggota
import com.example.ucpakhir.repository.AnggotaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EditAnggotaViewModel(private val repository: AnggotaRepository) : ViewModel() {
    private val _anggota = MutableStateFlow<Anggota?>(null)
    val anggota: StateFlow<Anggota?> = _anggota

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // Fungsi untuk mengambil anggota berdasarkan id
    fun fetchAnggotaById(idAnggota: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val fetchedAnggota = repository.getAnggotabyid(idAnggota)
                _anggota.value = fetchedAnggota
                _errorMessage.value = null
            } catch (e: Exception) {
                _anggota.value = null
                _errorMessage.value = "Gagal mengambil data anggota"
            } finally {
                _loading.value = false
            }
        }
    }

    // Fungsi untuk memperbarui anggota
    fun updateAnggota(idAnggota: String, anggota: Anggota) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.updateAnggota(idAnggota, anggota)
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Gagal memperbarui data anggota"
            } finally {
                _loading.value = false
            }
        }
    }
}