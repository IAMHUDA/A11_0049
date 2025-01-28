package com.example.ucpakhir.ui.pages.buku.view

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
import com.example.ucpakhir.model.Buku
import com.example.ucpakhir.ui.PenyediaViewModel
import com.example.ucpakhir.ui.pages.buku.viewmodel.EditBukuViewModel
import com.example.ucpakhir.ui.navigation.DestinasiNavigasi



object DestinasiEditBuku : DestinasiNavigasi {
    override val route = "editbuku"
    override val titleRes = "Edit Buku"
}

@Composable
fun editBuku(
    idBuku: String,
    navigateBack: () -> Unit,
    viewModel: EditBukuViewModel = viewModel(factory = PenyediaViewModel.Factory), // Inject ViewModel
    onUpdateBuku: (Buku) -> Unit
) {
    val buku by viewModel.buku.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    // Fetch buku data saat pertama kali masuk
    LaunchedEffect(idBuku) {
        viewModel.fetchBukuById(idBuku)
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
            buku?.let { buku ->
                EditForm(
                    buku = buku,
                    onUpdateBuku = { updatedBuku ->
                        viewModel.updateBuku(idBuku, updatedBuku)
                        onUpdateBuku(updatedBuku)
                        navigateBack()
                    }
                )
            }
        }
    }
}

@Composable
fun EditForm(
    buku: Buku,
    onUpdateBuku: (Buku) -> Unit
) {
    var judul by remember { mutableStateOf(buku.judul) }
    var penulis by remember { mutableStateOf(buku.penulis) }
    var kategori by remember { mutableStateOf(buku.kategori) }
    var status by remember { mutableStateOf(buku.status) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Edit Buku", style = MaterialTheme.typography.titleLarge)

        TextField(
            value = judul,
            onValueChange = { judul = it },
            label = { Text("Judul") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = penulis,
            onValueChange = { penulis = it },
            label = { Text("Penulis") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = kategori,
            onValueChange = { kategori = it },
            label = { Text("Kategori") },
            modifier = Modifier.fillMaxWidth()
        )

        // Pilihan status menggunakan Dropdown
        Box(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = status,
                onValueChange = {},
                label = { Text("Status") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Expand Dropdown",
                        modifier = Modifier.clickable { expanded = !expanded }
                    )
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                // Menggunakan list status dengan dua nilai
                val statusList = listOf("available", "not available")
                statusList.forEach { item ->
                    DropdownMenuItem(
                        onClick = {
                            status = item // Set nilai status
                            expanded = false
                        },
                        text = { Text(item) }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val updatedBuku = buku.copy(
                    judul = judul,
                    penulis = penulis,
                    kategori = kategori,
                    status = status
                )
                onUpdateBuku(updatedBuku)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update")
        }
    }
}
