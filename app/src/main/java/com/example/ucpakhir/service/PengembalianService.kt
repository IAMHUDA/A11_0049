package com.example.ucpakhir.service

import com.example.ucpakhir.model.AllPengembalianResponse
import com.example.ucpakhir.model.Anggota
import com.example.ucpakhir.model.Buku
import com.example.ucpakhir.model.Pengembalian
import com.example.ucpakhir.model.PengembalianDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PengembalianService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("pengembalian/pengembalian")
    suspend fun insertPengembalian(@Body pengembalian: Pengembalian)

    @GET("pengembalian/.")
    suspend fun getAllPengembalian(): AllPengembalianResponse

    @GET("pengembalian/{idPengembalian}")
    suspend fun getPengembalianbyid(@Path("idPengembalian")idPengembalian: String): PengembalianDetailResponse

    @PUT("pengembalian/{idPengembalian}")
    suspend fun updatePengembalian(@Path("idPengembalian")idPengembalian: String, @Body Pengembalian: Pengembalian)

    @DELETE("pengembalian/{idPengembalian}")
    suspend fun deletePengembalian(@Path("idPengembalian")idPengembalian: String): Response<Void>

    @GET("pengembalian/anggota")
    suspend fun getAllAnggota(): List<Anggota>

    // Tambahkan metode untuk mendapatkan semua buku
    @GET("pengembalian/buku")
    suspend fun getAllBuku(): List<Buku>

}

