package com.example.ucpakhir.repository

import com.example.ucpakhir.model.AllPeminjamanResponse
import com.example.ucpakhir.model.Anggota
import com.example.ucpakhir.model.Buku
import com.example.ucpakhir.model.Peminjaman
import com.example.ucpakhir.service.PeminjamanService
import okio.IOException

interface PeminjamanRepository{
    suspend fun insertPeminjaman(peminjaman: Peminjaman)
    suspend fun getPeminjaman(): AllPeminjamanResponse
    suspend fun updatePeminjaman(idPeminjaman: String, peminjaman: Peminjaman)
    suspend fun deletePeminjaman(idPeminjaman: String)
    suspend fun getPeminjamanbyid(idPeminjaman: String): Peminjaman
    suspend fun getAllAnggota(): List<Anggota> // Menambahkan fungsi untuk mendapatkan semua anggota
    suspend fun getAllBuku(): List<Buku>
}

class NetworkPeminjamanRepository(
    private val peminjamanApiService: PeminjamanService
): PeminjamanRepository{
    override suspend fun insertPeminjaman(peminjaman: Peminjaman) {
        peminjamanApiService.insertPeminjaman(peminjaman)
    }

    override suspend fun getPeminjaman(): AllPeminjamanResponse =
        peminjamanApiService.getAllPeminjaman()


    override suspend fun updatePeminjaman(idPeminjaman: String, peminjaman: Peminjaman) {
        peminjamanApiService.updatePeminjaman(idPeminjaman,peminjaman)
    }

    override suspend fun deletePeminjaman(idPeminjaman: String) {
        try {
            val response = peminjamanApiService.deletePeminjaman(idPeminjaman)
            if (!response.isSuccessful){
                throw IOException("failed to delete peminjaman : HTTP Status code:\" + \"${(response.code())}")
            }else{
                response.message()
                println(response.message())
            }
        }
        catch (e:Exception){
            throw e
        }
    }

    override suspend fun getPeminjamanbyid(idPeminjaman: String): Peminjaman {
        return peminjamanApiService.getPeminjamanbyid(idPeminjaman).data
    }

    override suspend fun getAllAnggota(): List<Anggota> {
        // Panggil API untuk mendapatkan semua anggota
        return peminjamanApiService.getAllAnggota() // Pastikan Anda memiliki metode ini di PeminjamanService
    }

    override suspend fun getAllBuku(): List<Buku> {
        // Panggil API untuk mendapatkan semua buku
        return peminjamanApiService.getAllBuku() // Pastikan Anda memiliki metode ini di PeminjamanService
    }

}