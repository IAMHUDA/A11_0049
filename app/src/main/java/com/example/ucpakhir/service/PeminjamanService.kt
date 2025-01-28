package com.example.ucpakhir.service

import com.example.ucpakhir.model.AllPeminjamanResponse
import com.example.ucpakhir.model.Anggota
import com.example.ucpakhir.model.Buku
import com.example.ucpakhir.model.Peminjaman
import com.example.ucpakhir.model.PeminjamanDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PeminjamanService {
    
    @Headers(
    "Accept: application/json",
    "Content-Type: application/json"
)

@POST("peminjaman/peminjaman")
suspend fun insertPeminjaman(@Body peminjaman: Peminjaman)

    @GET("peminjaman/.")
    suspend fun getAllPeminjaman(): AllPeminjamanResponse

    @GET("peminjaman/{idPeminjaman}")
    suspend fun getPeminjamanbyid(@Path("idPeminjaman")idPeminjaman: String): PeminjamanDetailResponse

    @PUT("peminjaman/{idPeminjaman}")
    suspend fun updatePeminjaman(@Path("idPeminjaman")idPeminjaman: String, @Body Peminjaman: Peminjaman)

    @DELETE("peminjaman/{idPeminjaman}")
    suspend fun deletePeminjaman(@Path("idPeminjaman")idPeminjaman: String): Response<Void>

    @GET("peminjaman/anggota")
    suspend fun getAllAnggota(): List<Anggota>

    // Tambahkan metode untuk mendapatkan semua buku
    @GET("peminjaman/buku")
    suspend fun getAllBuku(): List<Buku>
}