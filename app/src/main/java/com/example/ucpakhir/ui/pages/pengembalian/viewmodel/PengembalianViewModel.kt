package com.example.ucpakhir.ui.pages.pengembalian.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucpakhir.model.Pengembalian
import com.example.ucpakhir.repository.PengembalianRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class HomeUiState {
    data class Success(val  Pengembalian: List<Pengembalian>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class PengembalianViewModel(private val pengembalian: PengembalianRepository) : ViewModel() {
    var pengembalianUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    var searchQuery by mutableStateOf("") // Query pencarian
        private set

    var filteredPengembalian: List<Pengembalian> by mutableStateOf(emptyList()) // Hasil pencarian
        private set

    init {
        getPengembalian()
    }

    fun getPengembalian() {
        viewModelScope.launch {
            pengembalianUiState = HomeUiState.Loading
            pengembalianUiState = try {
                val pengembalianList = pengembalian.getPengembalian().data
                filteredPengembalian = pengembalianList
                HomeUiState.Success(pengembalianList)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }



    fun deletePengembalian(idPengembalian: String) {
        viewModelScope.launch {
            try {
                pengembalian.deletePengembalian(idPengembalian)
                // Refresh data setelah penghapusan
                getPengembalian()
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }
}