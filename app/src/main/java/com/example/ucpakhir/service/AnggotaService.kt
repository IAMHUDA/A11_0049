package com.example.ucpakhir.service

import com.example.ucpakhir.model.AllAnggotaResponse
import com.example.ucpakhir.model.Anggota
import com.example.ucpakhir.model.AnggotaDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AnggotaService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @POST("anggota/anggota")
    suspend fun insertAnggota(@Body anggota: Anggota)

    @GET("anggota/.")
    suspend fun getAllAnggota(): AllAnggotaResponse

    @GET("anggota/{idAnggota}")
    suspend fun getAnggotabyid(@Path("idAnggota")idAnggota: String): AnggotaDetailResponse

    @PUT("anggota/{idAnggota}")
    suspend fun updateAnggota(@Path("idAnggota")idAnggota: String, @Body Anggota: Anggota)

    @DELETE("anggota/{idAnggota}")
    suspend fun deleteAnggota(@Path("idAnggota")idAnggota: String): Response<Void>
}