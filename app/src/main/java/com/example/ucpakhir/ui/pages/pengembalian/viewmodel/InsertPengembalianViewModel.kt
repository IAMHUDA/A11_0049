package com.example.ucpakhir.ui.pages.pengembalian.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucpakhir.model.Anggota
import com.example.ucpakhir.model.Buku
import com.example.ucpakhir.model.Peminjaman
import com.example.ucpakhir.model.Pengembalian
import com.example.ucpakhir.repository.AnggotaRepository
import com.example.ucpakhir.repository.BukuRepository
import com.example.ucpakhir.repository.PeminjamanRepository
import com.example.ucpakhir.repository.PengembalianRepository
import kotlinx.coroutines.launch
import kotlin.math.tan

class InsertPengembalianViewModel(
    private val pengembalian: PengembalianRepository,
    private val buku: BukuRepository,
    private val anggota: AnggotaRepository,
    private val peminjaman: PeminjamanRepository
): ViewModel(){
    var pengembalianUiState by mutableStateOf(PengembalianUiState())
        private set

    var bukuList by mutableStateOf(listOf<Buku>())
        private set

    var anggotaList by mutableStateOf(listOf<Anggota>())
        private set

    var peminjamanList by mutableStateOf(listOf<Peminjaman>())
        private set

    init {
        loadData()
    }

    fun updateInsertPengembalianState(pengembalianUiEvent: PengembalianUiEvent){
        pengembalianUiState = PengembalianUiState(pengembalianUiEvent = pengembalianUiEvent)
    }

    suspend fun InsertPengembalian(){
        viewModelScope.launch {
            try {
                pengembalian.insertPengembalian(pengembalianUiState.pengembalianUiEvent.toPengembalian())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun loadData(){
        viewModelScope.launch {
            try {
                bukuList = buku.getBuku().data
                anggotaList = anggota.getAnggota().data
                peminjamanList = peminjaman.getPeminjaman().data
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }




}

data class PengembalianUiState(
    val pengembalianUiEvent: PengembalianUiEvent = PengembalianUiEvent()
)

data class PengembalianUiEvent(
    val idPengembalian: String = "",
    val namaAnggota: String = "",
    val judulBuku: String = "",
    val tanggaldikembalikan: String ="",
    val idPeminjaman: String = ""
)


fun PengembalianUiEvent.toPengembalian(): Pengembalian = Pengembalian(
    idPengembalian =  idPengembalian,
    namaAnggota = namaAnggota,
    judulBuku = judulBuku,
    tanggaldikembalikan = tanggaldikembalikan,
    idPeminjaman = idPeminjaman
)

fun Pengembalian.toUiStatePengembalian(): PengembalianUiState = PengembalianUiState(
    pengembalianUiEvent = toPengembalianUiEvent()
)

fun Pengembalian.toPengembalianUiEvent(): PengembalianUiEvent = PengembalianUiEvent(
    idPengembalian =  idPengembalian,
    namaAnggota = namaAnggota,
    judulBuku = judulBuku,
    tanggaldikembalikan = tanggaldikembalikan,
    idPeminjaman = idPeminjaman,
)