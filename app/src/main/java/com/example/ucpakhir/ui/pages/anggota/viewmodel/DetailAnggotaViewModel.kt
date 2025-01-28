package com.example.ucpakhir.ui.pages.anggota.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ucpakhir.model.Anggota
import com.example.ucpakhir.repository.AnggotaRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class DetailAnggotaViewModelFactory(private val repository: AnggotaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailAnggotaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailAnggotaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class DetailAnggotaViewModel(private val anggota: AnggotaRepository) : ViewModel() {

    // Mengambil data anggota berdasarkan id anggota
    fun getMahasiswabyNim(idAnggota: String, onResult: (Anggota?) -> Unit) {
        viewModelScope.launch {
            val anggota = try {
                anggota.getAnggotabyid(idAnggota)
            } catch (e: IOException) {
                null // Tangani error jika ada masalah koneksi
            } catch (e: HttpException) {
                null // Tangani error jika respons dari server salah
            }
            onResult(anggota)
        }
    }


}