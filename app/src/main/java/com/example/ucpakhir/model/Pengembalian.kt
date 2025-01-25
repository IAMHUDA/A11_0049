package com.example.ucpakhir.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllPengembalianResponse(
    val status: Boolean,
    val message: String,
    val data: List<Pengembalian>
)

@Serializable
data class PengembalianDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Pengembalian
)

@Serializable
data class Pengembalian(
    @SerialName("id_pengembalian") val idPengembalian:String,
    @SerialName("judul_buku") val judulBuku: String,
    @SerialName("nama_anggota") val namaAnggota: String,
    @SerialName("tanggal_dikembalikan") val tanggaldikembalikan: String,
    @SerialName("id_peminjaman") val idPeminjaman: String
)