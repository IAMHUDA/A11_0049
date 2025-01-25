package com.example.ucpakhir.repository

import com.example.ucpakhir.model.AllAnggotaResponse
import com.example.ucpakhir.model.Anggota
import com.example.ucpakhir.service.AnggotaService
import java.io.IOException

interface AnggotaRepository{
    suspend fun insertAnggota(anggota: Anggota)
    suspend fun getAnggota(): AllAnggotaResponse
    suspend fun updateAnggota(idAnggota: String, anggota: Anggota)
    suspend fun deleteAnggota(idAnggota: String)
    suspend fun getAnggotabyid(idAnggota: String): Anggota
}

class NetworkAnggotaRepository(
    private val anggotaApiService: AnggotaService
): AnggotaRepository{
    override suspend fun insertAnggota(anggota: Anggota) {
        anggotaApiService.insertAnggota(anggota)
    }

    override suspend fun getAnggota(): AllAnggotaResponse =
        anggotaApiService.getAllAnggota()


    override suspend fun updateAnggota(idAnggota: String, anggota: Anggota) {
        anggotaApiService.updateAnggota(idAnggota,anggota)
    }

    override suspend fun deleteAnggota(idAnggota: String) {
        try {
            val response = anggotaApiService.deleteAnggota(idAnggota)
            if (!response.isSuccessful){
                throw IOException("Failed to delete kontak. HTTP Status code:" + "${(response.code())}")
            }else{
                response.message()
                println(response.message())
            }
        }
        catch (e: Exception){
            throw e
        }

    }

    override suspend fun getAnggotabyid(idAnggota: String): Anggota {
        return anggotaApiService.getAnggotabyid(idAnggota).data
    }

}