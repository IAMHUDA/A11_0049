package com.example.ucpakhir.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucpakhir.BukuApplications
import com.example.ucpakhir.ui.pages.anggota.viewmodel.AnggotaViewModel
import com.example.ucpakhir.ui.pages.anggota.viewmodel.DetailAnggotaViewModel
import com.example.ucpakhir.ui.pages.anggota.viewmodel.EditAnggotaViewModel
import com.example.ucpakhir.ui.pages.anggota.viewmodel.InsertAnggotaViewModel
import com.example.ucpakhir.ui.pages.buku.viewmodel.BukuViewModel
import com.example.ucpakhir.ui.pages.buku.viewmodel.DetailBukuViewModel
import com.example.ucpakhir.ui.pages.buku.viewmodel.EditBukuViewModel
import com.example.ucpakhir.ui.pages.buku.viewmodel.InsertBukuViewModel
import com.example.ucpakhir.ui.pages.peminjaman.viewmodel.DetailPeminjamanViewModel
import com.example.ucpakhir.ui.pages.peminjaman.viewmodel.EditPeminjamanViewModel
import com.example.ucpakhir.ui.pages.peminjaman.viewmodel.InsertPeminjamanViewModel

import com.example.ucpakhir.ui.pages.peminjaman.viewmodel.PeminjamanViewModel
import com.example.ucpakhir.ui.pages.pengembalian.viewmodel.DetailPengembalianViewModel
import com.example.ucpakhir.ui.pages.pengembalian.viewmodel.EditPengembalianViewModel
import com.example.ucpakhir.ui.pages.pengembalian.viewmodel.InsertPengembalianViewModel
import com.example.ucpakhir.ui.pages.pengembalian.viewmodel.PengembalianViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { BukuViewModel(aplikasiBuku().containerBuku.bukuRepository) }
        initializer { InsertBukuViewModel(aplikasiBuku().containerBuku.bukuRepository) }
        initializer { DetailBukuViewModel(aplikasiBuku().containerBuku.bukuRepository) }
        initializer { EditBukuViewModel(aplikasiBuku().containerBuku.bukuRepository) }
        initializer { AnggotaViewModel(aplikasiBuku().containerBuku.anggotaRepository) }
        initializer { InsertAnggotaViewModel(aplikasiBuku().containerBuku.anggotaRepository) }
        initializer { DetailAnggotaViewModel(aplikasiBuku().containerBuku.anggotaRepository) }
        initializer { EditAnggotaViewModel(aplikasiBuku().containerBuku.anggotaRepository) }
        initializer { PeminjamanViewModel(aplikasiBuku().containerBuku.peminjamanRepository) }
        initializer { InsertPeminjamanViewModel(
            aplikasiBuku().containerBuku.peminjamanRepository,
            aplikasiBuku().containerBuku.bukuRepository,
            aplikasiBuku().containerBuku.anggotaRepository)
        }
        initializer { DetailPeminjamanViewModel(aplikasiBuku().containerBuku.peminjamanRepository) }
        initializer { EditPeminjamanViewModel(
            createSavedStateHandle(),
            aplikasiBuku().containerBuku.peminjamanRepository,
            aplikasiBuku().containerBuku.bukuRepository,
            aplikasiBuku().containerBuku.anggotaRepository)
        }
        initializer { PengembalianViewModel(aplikasiBuku().containerBuku.pengembalianRepository) }
        initializer { InsertPengembalianViewModel(
            aplikasiBuku().containerBuku.pengembalianRepository,
            aplikasiBuku().containerBuku.bukuRepository,
            aplikasiBuku().containerBuku.anggotaRepository,
            aplikasiBuku().containerBuku.peminjamanRepository)
        }
        initializer { DetailPengembalianViewModel(aplikasiBuku().containerBuku.pengembalianRepository) }
        initializer { EditPengembalianViewModel(
            createSavedStateHandle(),
            aplikasiBuku().containerBuku.pengembalianRepository,
            aplikasiBuku().containerBuku.bukuRepository,
            aplikasiBuku().containerBuku.anggotaRepository,
            aplikasiBuku().containerBuku.peminjamanRepository) }

    }

}

fun CreationExtras.aplikasiBuku(): BukuApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as  BukuApplications)

