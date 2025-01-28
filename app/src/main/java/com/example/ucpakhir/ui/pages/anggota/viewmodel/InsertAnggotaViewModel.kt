package com.example.ucpakhir.ui.pages.anggota.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucpakhir.model.Anggota
import com.example.ucpakhir.repository.AnggotaRepository
import kotlinx.coroutines.launch

class InsertAnggotaViewModel(private val anggota: AnggotaRepository): ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertAnggotaState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    fun insertAnggota() {
        viewModelScope.launch {
            try {
                anggota.insertAnggota(uiState.insertUiEvent.toAnggota())
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


data class InsertUiEvent(
    val idAnggota: String = "",
    val nama: String = "",
    val email: String = "",
    val noTelpon: String = "",

)

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)



fun InsertUiEvent.toAnggota(): Anggota = Anggota(
    idAnggota= idAnggota,
    nama = nama,
    email = email,
    noTelpon = noTelpon,
)

fun Anggota.toUiStateAnggota(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Anggota.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idAnggota= idAnggota,
    nama = nama,
    email = email,
    noTelpon = noTelpon,

)