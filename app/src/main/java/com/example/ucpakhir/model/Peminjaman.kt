package com.example.ucpakhir.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllPeminjamanResponse(
    val status: Boolean,
    val message: String,
    val data: List<Peminjaman>
)

@Serializable
data class PeminjamanDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Peminjaman
)

@Serializable
data class AnggotaResponse(
    val status: Boolean,
    val message: String,
    val data: List<Anggota>
)

@Serializable
data class BukuResponse(
    val status: Boolean,
    val message: String,
    val data: List<Buku>
)



@Serializable
data class Peminjaman(
    @SerialName("id_peminjaman") val idPeminjaman: String,
    @SerialName("tanggal_peminjaman") val tanggalPeminjaman: String,
    @SerialName("tanggal_pengembalian") val tanggalPengembalian: String,
    @SerialName("judul_buku") val judulBuku: String,
    @SerialName("nama_anggota") val namaAnggota: String,

    @SerialName("id_anggota") val idAnggota: String? = null,
    @SerialName("id_buku") val idBuku: String? = null,
    val status: String
)

