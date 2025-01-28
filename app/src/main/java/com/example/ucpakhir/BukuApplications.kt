package com.example.ucpakhir

import android.app.Application
import com.example.ucpakhir.di.AppContainerBuku
import com.example.ucpakhir.di.BukuContainer

class BukuApplications:Application() {
    lateinit var containerBuku: AppContainerBuku
    override fun onCreate() {
        super.onCreate()
        containerBuku = BukuContainer()
    }
}