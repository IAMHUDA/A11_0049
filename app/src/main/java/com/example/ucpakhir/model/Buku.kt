package com.example.ucpakhir.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllBukuResponse(
    val status: Boolean,
    val message: String,
    val data: List<Buku>
)

@Serializable
data class BukuDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Buku
)

@Serializable
data class Buku(
    @SerialName("id_buku")
    val idBuku: String,
    val judul: String,
    val penulis: String,
    val kategori: String,
    val status: String
)



