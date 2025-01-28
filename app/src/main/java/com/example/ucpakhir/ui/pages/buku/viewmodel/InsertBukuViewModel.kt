package com.example.ucpakhir.ui.pages.buku.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucpakhir.model.Buku
import com.example.ucpakhir.repository.BukuRepository
import kotlinx.coroutines.launch
import kotlinx.serialization.SerialName

class InsertBukuViewModel(private val buku: BukuRepository): ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateInsertBukuState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    suspend fun insertBuku() {
        viewModelScope.launch {
            try {
                buku.insertBuku(uiState.insertUiEvent.toBuku())
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


data class InsertUiEvent(
    val idBuku: String = "",
    val judul: String = "",
    val penulis: String = "",
    val kategori: String = "",
    val status: String = ""
)

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent()
)



fun InsertUiEvent.toBuku(): Buku = Buku(
    idBuku= idBuku,
    judul = judul,
    penulis = penulis,
    kategori= kategori,
    status = status
)

fun Buku.toUiStateBuku(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Buku.toInsertUiEvent(): InsertUiEvent = InsertUiEvent(
    idBuku= idBuku,
    judul = judul,
    penulis = penulis,
    kategori= kategori,
    status = status
)