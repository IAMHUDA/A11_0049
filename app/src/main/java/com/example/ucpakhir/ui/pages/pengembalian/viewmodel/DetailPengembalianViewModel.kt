package com.example.ucpakhir.ui.pages.pengembalian.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ucpakhir.model.Pengembalian
import com.example.ucpakhir.repository.PengembalianRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DetailPengembalianViewModelFactory(private val repository: PengembalianRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailPengembalianViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailPengembalianViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class DetailPengembalianViewModel(private val pengembalian: PengembalianRepository) : ViewModel() {

    // Mengambil data pengembalian berdasarkan id pengembalian
    fun getPengembalianbyid(idPengembalian: String, onResult: (Pengembalian?) -> Unit) {
        viewModelScope.launch {
            val pengembalian = try {
                pengembalian.getPengembalianbyid(idPengembalian)
            } catch (e: IOException) {
                null // Tangani error jika ada masalah koneksi
            } catch (e: HttpException) {
                null // Tangani error jika respons dari server salah
            }
            onResult(pengembalian)
        }
    }


}