package com.example.ucpakhir.di

import com.example.ucpakhir.repository.AnggotaRepository
import com.example.ucpakhir.repository.BukuRepository
import com.example.ucpakhir.repository.NetworkAnggotaRepository
import com.example.ucpakhir.repository.NetworkBukuRepository
import com.example.ucpakhir.repository.NetworkPeminjamanRepository
import com.example.ucpakhir.repository.NetworkPengembalianRepository
import com.example.ucpakhir.repository.PeminjamanRepository
import com.example.ucpakhir.repository.PengembalianRepository
import com.example.ucpakhir.service.AnggotaService
import com.example.ucpakhir.service.BukuService
import com.example.ucpakhir.service.PeminjamanService
import com.example.ucpakhir.service.PengembalianService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainerBuku{
    val bukuRepository: BukuRepository
    val anggotaRepository: AnggotaRepository
    val peminjamanRepository: PeminjamanRepository
    val pengembalianRepository: PengembalianRepository
}

class BukuContainer: AppContainerBuku{

    private val baseUrl = "http://10.0.2.2:3000/api/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val bukuService: BukuService by lazy {
        retrofit.create(BukuService::class.java)
    }

    override val bukuRepository: BukuRepository by lazy {
        NetworkBukuRepository(bukuService)
    }

    private val anggotaService: AnggotaService by lazy {
        retrofit.create(AnggotaService::class.java)
    }

    override val anggotaRepository: AnggotaRepository by lazy {
        NetworkAnggotaRepository(anggotaService)
    }

    private val  peminjamanService: PeminjamanService by lazy {
        retrofit.create(PeminjamanService::class.java)
    }

    override val peminjamanRepository: PeminjamanRepository by lazy {
        NetworkPeminjamanRepository(peminjamanService)
    }

    private val pengembalianService: PengembalianService by lazy {
        retrofit.create(PengembalianService::class.java)
    }

    override val pengembalianRepository: PengembalianRepository by lazy {
        NetworkPengembalianRepository(pengembalianService)
    }


}