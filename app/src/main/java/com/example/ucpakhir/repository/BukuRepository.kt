package com.example.ucpakhir.repository

import com.example.ucpakhir.model.AllBukuResponse
import com.example.ucpakhir.model.Buku
import com.example.ucpakhir.service.BukuService
import java.io.IOException

interface BukuRepository{
    suspend fun insertBuku(buku: Buku)
    suspend fun getBuku(): AllBukuResponse
    suspend fun updateBuku(idBuku: String, buku: Buku)
    suspend fun deleteBuku(idBuku: String)
    suspend fun getBukubyid(idBuku: String): Buku
}

class NetworkBukuRepository(
    private val bukuApiService: BukuService
): BukuRepository{
    override suspend fun insertBuku(buku: Buku) {
        bukuApiService.insertBuku(buku)
    }

    override suspend fun getBuku(): AllBukuResponse =
        bukuApiService.getAllBuku()


    override suspend fun updateBuku(idBuku: String, buku: Buku) {
        bukuApiService.updateBuku(idBuku,buku)
    }

    override suspend fun deleteBuku(idBuku: String) {
        try {
            val response = bukuApiService.deleteBuku(idBuku)
            if (!response.isSuccessful){
                throw IOException("Failed to delete buku. HTTP Status code:" + "${(response.code())}")
            }else{
                response.message()
                println(response.message())
            }
        }
        catch (e: Exception){
            throw e
        }

    }

    override suspend fun getBukubyid(idBuku: String): Buku {
        return bukuApiService.getBukubyid(idBuku).data
    }

}