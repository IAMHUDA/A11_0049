package com.example.ucpakhir.ui.pages.buku.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucpakhir.model.Buku
import com.example.ucpakhir.repository.BukuRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EditBukuViewModel(private val repository: BukuRepository) : ViewModel() {
    private val _buku = MutableStateFlow<Buku?>(null)
    val buku: StateFlow<Buku?> = _buku

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // Fungsi untuk mengambil buku berdasarkan id
    fun fetchBukuById(idBuku: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val fetchedBuku = repository.getBukubyid(idBuku)
                _buku.value = fetchedBuku
                _errorMessage.value = null
            } catch (e: Exception) {
                _buku.value = null
                _errorMessage.value = "Gagal mengambil data buku"
            } finally {
                _loading.value = false
            }
        }
    }

    // Fungsi untuk memperbarui buku
    fun updateBuku(idBuku: String, buku: Buku) {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.updateBuku(idBuku, buku)
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Gagal memperbarui data buku"
            } finally {
                _loading.value = false
            }
        }
    }
}
