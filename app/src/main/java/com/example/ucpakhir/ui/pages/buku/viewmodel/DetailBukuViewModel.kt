package com.example.ucpakhir.ui.pages.buku.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ucpakhir.model.Buku
import com.example.ucpakhir.repository.BukuRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DetailBukuViewModelFactory(private val repository: BukuRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailBukuViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailBukuViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class DetailBukuViewModel(private val buku: BukuRepository) : ViewModel() {

    // Mengambil data buku berdasarkan id buku
    fun getBukubyid(idBuku: String, onResult: (Buku?) -> Unit) {
        viewModelScope.launch {
            val buku = try {
                buku.getBukubyid(idBuku)
            } catch (e: IOException) {
                null // Tangani error jika ada masalah koneksi
            } catch (e: HttpException) {
                null // Tangani error jika respons dari server salah
            }
            onResult(buku)
        }
    }


}