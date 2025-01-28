package com.example.ucpakhir.ui.pages.peminjaman.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucpakhir.model.Peminjaman
import com.example.ucpakhir.repository.PeminjamanRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


sealed class HomeUiState {
    data class Success(val  Peminjaman: List<Peminjaman>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class PeminjamanViewModel(private val peminjaman: PeminjamanRepository) : ViewModel() {
    var peminjamanUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    var searchQuery by mutableStateOf("") // Query pencarian
        private set

    var filteredPeminjaman: List<Peminjaman> by mutableStateOf(emptyList()) // Hasil pencarian
        private set

    init {
        getPeminjaman()
    }

    fun getPeminjaman() {
        viewModelScope.launch {
            peminjamanUiState = HomeUiState.Loading
            peminjamanUiState = try {
                val peminjamanList = peminjaman.getPeminjaman().data
                filteredPeminjaman = peminjamanList
                HomeUiState.Success(peminjamanList)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }



    fun deletePeminjaman(idPeminjaman: String) {
        viewModelScope.launch {
            try {
                peminjaman.deletePeminjaman(idPeminjaman)
                // Refresh data setelah penghapusan
                getPeminjaman()
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }
}