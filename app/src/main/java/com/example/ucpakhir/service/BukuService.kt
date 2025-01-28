package com.example.ucpakhir.service

import com.example.ucpakhir.model.AllBukuResponse
import com.example.ucpakhir.model.Buku
import com.example.ucpakhir.model.BukuDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface BukuService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    
    @POST("buku/buku")
    suspend fun insertBuku(@Body buku: Buku)
    
    @GET("buku/.")
    suspend fun getAllBuku(): AllBukuResponse
    
    @GET("buku/{idBuku}")
    suspend fun getBukubyid(@Path("idBuku")idBuku: String): BukuDetailResponse
    
    @PUT("buku/{idBuku}")
    suspend fun updateBuku(@Path("idBuku")idBuku: String, @Body Buku: Buku)
    
    @DELETE("buku/{idBuku}")
    suspend fun deleteBuku(@Path("idBuku")idBuku: String): Response<Void>
}