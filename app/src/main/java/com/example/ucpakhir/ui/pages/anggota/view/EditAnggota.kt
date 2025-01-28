package com.example.ucpakhir.ui.pages.anggota.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucpakhir.model.Anggota
import com.example.ucpakhir.ui.PenyediaViewModel
import com.example.ucpakhir.ui.pages.anggota.viewmodel.EditAnggotaViewModel
import com.example.ucpakhir.ui.navigation.DestinasiNavigasi

object DestinasiEditAnggota : DestinasiNavigasi {
    override val route = "editanggota"
    override val titleRes = "Edit Anggota"
}

@Composable
fun editAnggota(
    idAnggota: String,
    navigateBack: () -> Unit,
    viewModel: EditAnggotaViewModel = viewModel(factory = PenyediaViewModel.Factory), // Inject ViewModel
    onUpdateAnggota: (Anggota) -> Unit
) {
    val anggota by viewModel.anggota.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    // Fetch anggota data saat pertama kali masuk
    LaunchedEffect(idAnggota) {
        viewModel.fetchAnggotaById(idAnggota)
    }

    // Layout
    Box(modifier = Modifier.fillMaxSize()) {
        if (loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else if (errorMessage != null) {
            Text(
                text = errorMessage ?: "Error",
                color = Color.Red,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            anggota?.let { anggota ->
                EditForm(
                    anggota = anggota,
                    onUpdateAnggota = { updatedAnggota ->
                        viewModel.updateAnggota(idAnggota, updatedAnggota)
                        onUpdateAnggota(updatedAnggota)
                        navigateBack()
                    }
                )
            }
        }
    }
}

@Composable
fun EditForm(
    anggota: Anggota,
    onUpdateAnggota: (Anggota) -> Unit
) {
    var nama by remember { mutableStateOf(anggota.nama) }
    var email by remember { mutableStateOf(anggota.email) }
    var noTelpon by remember { mutableStateOf(anggota.noTelpon) }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Edit Anggota", style = MaterialTheme.typography.titleLarge)

        TextField(
            value = nama,
            onValueChange = { nama = it },
            label = { Text("nama") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Penulis") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = noTelpon,
            onValueChange = { noTelpon = it },
            label = { Text("Kategori") },
            modifier = Modifier.fillMaxWidth()
        )



        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val updatedAnggota = anggota.copy(
                    nama = nama,
                    email = email,
                    noTelpon = noTelpon,
                )
                onUpdateAnggota(updatedAnggota)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update")
        }
    }
}