package com.example.ucpakhir.ui.pages.anggota.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucpakhir.model.Anggota
import com.example.ucpakhir.repository.AnggotaRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class HomeUiState {
    data class Success(val  Anggota: List<Anggota>) : HomeUiState()
    object Error : HomeUiState()
    object Loading : HomeUiState()
}

class AnggotaViewModel (private val anggota: AnggotaRepository)
    : ViewModel(){
    var anggotaUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init {
        getAnggota()
    }

    fun getAnggota() {
        viewModelScope.launch {
            anggotaUiState = HomeUiState.Loading
            anggotaUiState = try {
                HomeUiState.Success(anggota.getAnggota().data)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

    fun deleteAnggota(idAnggota: String) {
        viewModelScope.launch {
            try {
                anggota.deleteAnggota(idAnggota)
            } catch (e: IOException) {
                HomeUiState.Error
            } catch (e: HttpException) {
                HomeUiState.Error
            }
        }
    }

}