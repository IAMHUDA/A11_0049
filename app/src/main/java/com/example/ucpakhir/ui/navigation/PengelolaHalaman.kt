package com.example.ucpakhir.ui.navigation



import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucpakhir.di.AppContainerBuku
import com.example.ucpakhir.ui.pages.anggota.view.AnggotaScreen
import com.example.ucpakhir.ui.pages.anggota.view.DestinasiAnggota
import com.example.ucpakhir.ui.pages.anggota.view.DestinasiDetailAnggota
import com.example.ucpakhir.ui.pages.anggota.view.DestinasiEditAnggota
import com.example.ucpakhir.ui.pages.anggota.view.DestinasiInsertAnggota
import com.example.ucpakhir.ui.pages.anggota.view.DetailAnggota
import com.example.ucpakhir.ui.pages.anggota.view.InsertAnggota
import com.example.ucpakhir.ui.pages.anggota.view.editAnggota
import com.example.ucpakhir.ui.pages.buku.view.BukuScreen
import com.example.ucpakhir.ui.pages.buku.view.DestinasiBuku
import com.example.ucpakhir.ui.pages.buku.view.DestinasiDetailBuku
import com.example.ucpakhir.ui.pages.buku.view.DestinasiEditBuku
import com.example.ucpakhir.ui.pages.buku.view.DestinasiInsertBuku
import com.example.ucpakhir.ui.pages.buku.view.DetailBukuScreen
import com.example.ucpakhir.ui.pages.buku.view.InsertBuku
import com.example.ucpakhir.ui.pages.buku.view.editBuku
import com.example.ucpakhir.ui.pages.home.DestinasiHome
import com.example.ucpakhir.ui.pages.home.DestinasiJadwal
import com.example.ucpakhir.ui.pages.home.HomeScreen
import com.example.ucpakhir.ui.pages.home.JadwalScreen
import com.example.ucpakhir.ui.pages.peminjaman.view.DestinasiDetailPeminjaman
import com.example.ucpakhir.ui.pages.peminjaman.view.DestinasiEditPeminjaman
import com.example.ucpakhir.ui.pages.peminjaman.view.DestinasiInsertPeminjaman

import com.example.ucpakhir.ui.pages.peminjaman.view.DestinasiPeminjaman
import com.example.ucpakhir.ui.pages.peminjaman.view.DetailPeminjamanScreen
import com.example.ucpakhir.ui.pages.peminjaman.view.EditBukuScreen
import com.example.ucpakhir.ui.pages.peminjaman.view.InsertPeminjamanScreen

import com.example.ucpakhir.ui.pages.peminjaman.view.PeminjamanScreen
import com.example.ucpakhir.ui.pages.pengembalian.view.DestinasiDetailPengembalian
import com.example.ucpakhir.ui.pages.pengembalian.view.DestinasiEditPengembalian
import com.example.ucpakhir.ui.pages.pengembalian.view.DestinasiInsertPengembalian
import com.example.ucpakhir.ui.pages.pengembalian.view.DestinasiPengembalian
import com.example.ucpakhir.ui.pages.pengembalian.view.DetailPengembalianScreen
import com.example.ucpakhir.ui.pages.pengembalian.view.EditPengembalianScreen
import com.example.ucpakhir.ui.pages.pengembalian.view.InsertPengembalianScreen
import com.example.ucpakhir.ui.pages.pengembalian.view.PengembalianScreen

@Composable
fun PengelolaHalaman(
    appContainerBuku: AppContainerBuku,
    navController: NavHostController = rememberNavController(),
){
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier
    ){
        // Rute untuk halaman Home
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToBuku = { navController.navigate(DestinasiBuku.route) },
                navigateToAnggota = { navController.navigate(DestinasiAnggota.route) },
                navigateToPeminjaman = { navController.navigate(DestinasiPeminjaman.route) },
                navigateToPengembalian = { navController.navigate(DestinasiPengembalian.route) },
                navigateToJadwal = {navController.navigate(DestinasiJadwal.route)}
            )
        }



        // Rute untuk halaman Buku
        composable(DestinasiBuku.route) {
            BukuScreen(
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) {
                            inclusive = true
                        }
                    }
                },
                onDetailClick = { idBuku -> navController.navigate("${DestinasiDetailBuku.route}/$idBuku") },
                navigateToEntry = { navController.navigate(DestinasiInsertBuku.route) },
                navigateToEdit = { idBuku -> navController.navigate("${DestinasiEditBuku.route}/$idBuku") }
            )
        }

        composable(DestinasiInsertBuku.route) {
            InsertBuku(navigateBack = {
                navController.navigate(DestinasiHome.route){
                    popUpTo(DestinasiHome.route){
                        inclusive = true
                    }
                }
            })
        }

        composable(DestinasiDetailBuku.route + "/{idBuku}") { backStackEntry ->
            val idbuku = backStackEntry.arguments?.getString("idBuku")
            if (idbuku != null) {
                DetailBukuScreen(idBuku = idbuku, repository = appContainerBuku.bukuRepository,navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) {
                            inclusive = true
                        }
                    }}) // Use the passed repository
            } else {
                // Handle error jika NIM null
            }
        }

        composable(DestinasiEditBuku.route + "/{idBuku}") { backStackEntry ->
            val idBuku = backStackEntry.arguments?.getString("idBuku")
            if (idBuku != null) {
                editBuku(
                    idBuku = idBuku,
                    navigateBack = {
                        navController.navigate(DestinasiBuku.route) {
                            popUpTo(DestinasiBuku.route) {
                                inclusive = true
                            }
                        }
                    },
                    onUpdateBuku = { updatedBuku ->
                        // Handle updated buku if necessary
                    }
                )
            } else {
                // Handle error jika idBuku null
                Text(
                    text = "Error: Buku tidak ditemukan.",
                    color = Color.Red,

                )
            }
        }

        // Rute untuk halaman Anggota
        composable(DestinasiAnggota.route) {
            AnggotaScreen(
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) {
                            inclusive = true
                        }
                    }
                },
                onDetailClick = { idAnggota -> navController.navigate("${DestinasiDetailAnggota.route}/$idAnggota") },
                navigateToltemEntry = { navController.navigate(DestinasiInsertAnggota.route) },
                navigateToEdit = { idAnggota -> navController.navigate("${DestinasiEditAnggota.route}/$idAnggota") }
            )
        }


        composable(DestinasiInsertAnggota.route) {
            InsertAnggota(navigateBack = {
                navController.navigate(DestinasiAnggota.route){
                    popUpTo(DestinasiAnggota.route){
                        inclusive = true
                    }
                }
            })
        }

        composable(DestinasiDetailAnggota.route + "/{idAnggota}") { backStackEntry ->
            val idanggota = backStackEntry.arguments?.getString("idAnggota")
            if (idanggota != null) {
                DetailAnggota(idAnggota = idanggota, repository = appContainerBuku.anggotaRepository,navigateBack = {
                    navController.navigate(DestinasiAnggota.route) {
                        popUpTo(DestinasiAnggota.route) {
                            inclusive = true
                        }
                    }}) // Use the passed repository
            } else {
                // Handle error jika NIM null
            }
        }


        composable(DestinasiEditAnggota.route + "/{idAnggota}") { backStackEntry ->
            val idAnggota = backStackEntry.arguments?.getString("idAnggota")
            if (idAnggota != null) {
                editAnggota(
                    idAnggota = idAnggota,
                    navigateBack = {
                        navController.navigate(DestinasiAnggota.route) {
                            popUpTo(DestinasiAnggota.route) {
                                inclusive = true
                            }
                        }
                    },
                    onUpdateAnggota = { updatedAnggota ->
                        // Handle updated buku if necessary
                    }
                )
            } else {
                // Handle error jika idBuku null
                Text(
                    text = "Error: Buku tidak ditemukan.",
                    color = Color.Red,

                    )
            }
        }

        // Rute untuk halaman Peminjaman

        composable(DestinasiPeminjaman.route) {
            PeminjamanScreen(
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) {
                            inclusive = true
                        }
                    }
                },
                onDetailClick = { idPeminjaman -> navController.navigate("${DestinasiDetailPeminjaman.route}/$idPeminjaman")},
                navigateToEntry = { navController.navigate(DestinasiInsertPeminjaman.route) },
                navigateToEdit = { idPeminjaman -> navController.navigate("${DestinasiEditPeminjaman.route}/$idPeminjaman") }
            )
        }

        composable(DestinasiInsertPeminjaman.route) {
            InsertPeminjamanScreen(navigateBack = {
                navController.navigate(DestinasiPeminjaman.route){
                    popUpTo(DestinasiPeminjaman.route){
                        inclusive = true
                    }
                }
            })
        }

        composable(DestinasiDetailPeminjaman.route + "/{idPeminjaman}") { backStackEntry ->
            val idpeminjaman = backStackEntry.arguments?.getString("idPeminjaman")
            if (idpeminjaman != null) {
                DetailPeminjamanScreen(idPeminjaman = idpeminjaman, repository = appContainerBuku.peminjamanRepository,navigateBack = {
                    navController.navigate(DestinasiPeminjaman.route) {
                        popUpTo(DestinasiPeminjaman.route) {
                            inclusive = true
                        }
                    }}) // Use the passed repository
            } else {
                // Handle error jika NIM null
            }
        }

        composable(
            DestinasiEditPeminjaman.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiEditPeminjaman.IDP){
                    type = NavType.StringType
                }
            )
        ){
            val idp = it.arguments?.getString(DestinasiEditPeminjaman.IDP)
            idp?.let {
                EditBukuScreen(
                    navigateBack = {navController.navigate(DestinasiPeminjaman.route) },
                )
            }
        }




        // Rute untuk halaman Pengembalian
        composable(DestinasiPengembalian.route) {
            PengembalianScreen(
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) {
                            inclusive = true
                        }
                    }
                },
                onDetailClick = { idPengembalian -> navController.navigate("${DestinasiDetailPengembalian.route}/$idPengembalian")},
                navigateToEntry = { navController.navigate(DestinasiInsertPengembalian.route) },
                navigateToEdit = { idPengembalian -> navController.navigate("${DestinasiEditPengembalian.route}/$idPengembalian") }
            )
        }

        composable(DestinasiDetailPengembalian.route + "/{idPengembalian}") { backStackEntry ->
            val idpengembalian = backStackEntry.arguments?.getString("idPengembalian")
            if (idpengembalian != null) {
                DetailPengembalianScreen(idPengembalian = idpengembalian, repository = appContainerBuku.pengembalianRepository,navigateBack = {
                    navController.navigate(DestinasiPengembalian.route) {
                        popUpTo(DestinasiPengembalian.route) {
                            inclusive = true
                        }
                    }}) // Use the passed repository
            } else {
                // Handle error jika NIM null
            }
        }


        composable(DestinasiInsertPengembalian.route) {
            InsertPengembalianScreen(navigateBack = {
                navController.navigate(DestinasiPengembalian.route){
                    popUpTo(DestinasiPengembalian.route){
                        inclusive = true
                    }
                }
            })
        }

        composable(
            DestinasiEditPengembalian.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiEditPengembalian.IDPN){
                    type = NavType.StringType
                }
            )
        ){
            val idpn = it.arguments?.getString(DestinasiEditPengembalian.IDPN)
            idpn?.let {
                EditPengembalianScreen(
                    navigateBack = {navController.navigate(DestinasiPengembalian.route) },
                )
            }
        }







        //halaman bottom /halaman tambahan
        composable(DestinasiJadwal.route) {
            JadwalScreen(
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) {
                            inclusive = true
                        }
                    }
                },
                onDetailClick = { idPengembalian -> navController.navigate("${DestinasiDetailPengembalian.route}/$idPengembalian")},
                navigateToEntry = { navController.navigate(DestinasiInsertPengembalian.route) },
                navigateToEdit = { idPengembalian -> navController.navigate("${DestinasiEditPengembalian.route}/$idPengembalian") }
            )
        }
    }
}