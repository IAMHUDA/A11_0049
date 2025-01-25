package com.example.ucpakhir.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllAnggotaResponse(
    val status: Boolean,
    val message: String,
    val data: List<Anggota>
)

@Serializable
data class AnggotaDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Anggota
)

@Serializable
data class Anggota(
    @SerialName("id_anggota")
    val idAnggota: String,
    val nama: String,
    val email: String,
    @SerialName("no_telpon")
    val noTelpon: String,
)