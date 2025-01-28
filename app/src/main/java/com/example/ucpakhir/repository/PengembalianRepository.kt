package com.example.ucpakhir.repository

import com.example.ucpakhir.model.AllPengembalianResponse
import com.example.ucpakhir.model.Anggota
import com.example.ucpakhir.model.Buku
import com.example.ucpakhir.model.Pengembalian
import com.example.ucpakhir.service.PengembalianService
import java.io.IOException

interface PengembalianRepository{
    suspend fun insertPengembalian(pengembalian: Pengembalian)
    suspend fun getPengembalian(): AllPengembalianResponse
    suspend fun updatePengembalian(idPengembalian: String, pengembalian: Pengembalian)
    suspend fun deletePengembalian(idPengembalian: String)
    suspend fun getPengembalianbyid(idPengembalian: String): Pengembalian
    suspend fun getAllAnggota(): List<Anggota> // Menambahkan fungsi untuk mendapatkan semua anggota
    suspend fun getAllBuku(): List<Buku>
}

class NetworkPengembalianRepository(
    private val pengembalianApiService: PengembalianService
): PengembalianRepository{
    override suspend fun insertPengembalian(pengembalian: Pengembalian) {
        pengembalianApiService.insertPengembalian(pengembalian)
    }

    override suspend fun getPengembalian(): AllPengembalianResponse =
        pengembalianApiService.getAllPengembalian()


    override suspend fun updatePengembalian(idPengembalian: String, pengembalian: Pengembalian) {
        pengembalianApiService.updatePengembalian(idPengembalian,pengembalian)
    }

    override suspend fun deletePengembalian(idPengembalian: String) {
        try {
            val response = pengembalianApiService.deletePengembalian(idPengembalian)
            if (!response.isSuccessful){
                throw IOException("failed to delete pengembalian : HTTP Status code:\" + \"${(response.code())}")
            }else{
                response.message()
                println(response.message())
            }
        }
        catch (e:Exception){
            throw e
        }
    }

    override suspend fun getPengembalianbyid(idPengembalian: String): Pengembalian {
        return pengembalianApiService.getPengembalianbyid(idPengembalian).data
    }

    override suspend fun getAllAnggota(): List<Anggota> {
        // Panggil API untuk mendapatkan semua anggota
        return pengembalianApiService.getAllAnggota() // Pastikan Anda memiliki metode ini di PengembalianService
    }

    override suspend fun getAllBuku(): List<Buku> {
        // Panggil API untuk mendapatkan semua buku
        return pengembalianApiService.getAllBuku() // Pastikan Anda memiliki metode ini di PengembalianService
    }

}