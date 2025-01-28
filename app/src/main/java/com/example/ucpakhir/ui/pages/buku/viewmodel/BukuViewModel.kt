package com.example.ucpakhir.ui.pages.buku.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucpakhir.model.Buku
import com.example.ucpakhir.repository.BukuRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class HomeUiState {
    data class Success(val  Buku: List<Buku>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class BukuViewModel(private val buku: BukuRepository) : ViewModel() {
    var bukuUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    var searchQuery by mutableStateOf("") // Query pencarian
        private set

    var filteredBuku: List<Buku> by mutableStateOf(emptyList()) // Hasil pencarian
        private set

    init {
        getBuku()
    }

    fun getBuku() {
        viewModelScope.launch {
            bukuUiState = HomeUiState.Loading
            bukuUiState = try {
                val bukuList = buku.getBuku().data
                filteredBuku = bukuList
                HomeUiState.Success(bukuList)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun onSearch(query: String) {
        searchQuery = query
        if (bukuUiState is HomeUiState.Success) {
            val allBuku = (bukuUiState as HomeUiState.Success).Buku
            filteredBuku = if (query.isBlank()) {
                allBuku // Tampilkan semua data jika query kosong
            } else {
                allBuku.filter {
                    it.judul.contains(query, ignoreCase = true) ||
                            it.penulis.contains(query, ignoreCase = true) ||
                            it.kategori.contains(query, ignoreCase = true)
                }
            }
        }
    }

    fun deleteBuku(idBuku: String) {
        viewModelScope.launch {
            try {
                buku.deleteBuku(idBuku)
                // Refresh data setelah penghapusan
                getBuku()
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }
}
