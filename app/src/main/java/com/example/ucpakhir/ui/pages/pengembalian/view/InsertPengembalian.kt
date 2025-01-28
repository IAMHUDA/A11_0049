package com.example.ucpakhir.ui.pages.pengembalian.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucpakhir.model.Anggota
import com.example.ucpakhir.model.Buku
import com.example.ucpakhir.model.Peminjaman
import com.example.ucpakhir.ui.PenyediaViewModel
import com.example.ucpakhir.ui.customwidget.CostumeTopAppBar
import com.example.ucpakhir.ui.navigation.DestinasiNavigasi
import com.example.ucpakhir.ui.pages.pengembalian.viewmodel.InsertPengembalianViewModel
import com.example.ucpakhir.ui.pages.pengembalian.viewmodel.PengembalianUiEvent
import com.example.ucpakhir.ui.pages.pengembalian.viewmodel.PengembalianUiState
import kotlinx.coroutines.launch

object DestinasiInsertPengembalian : DestinasiNavigasi {
    override val route = "pengembalianinsert"
    override val titleRes = "Halaman Pengembalian insert"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertPengembalianScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPengembalianViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = "Tambah Pengembalian",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        PengembalianForm(
            pengembalianUiState = viewModel.pengembalianUiState,
            bukuList = viewModel.bukuList,
            anggotaList = viewModel.anggotaList,
            peminjamanList = viewModel.peminjamanList,
            onPengembalianValueChange = viewModel::updateInsertPengembalianState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.InsertPengembalian()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun PengembalianForm(
    pengembalianUiState: PengembalianUiState,
    bukuList: List<Buku>,
    anggotaList: List<Anggota>,
    peminjamanList:List<Peminjaman>,
    onPengembalianValueChange: (PengembalianUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        // Field ID Pengembalian
        OutlinedTextField(
            value = pengembalianUiState.pengembalianUiEvent.idPengembalian,
            onValueChange = { onPengembalianValueChange(pengembalianUiState.pengembalianUiEvent.copy(idPengembalian = it)) },
            label = { Text("ID Pengembalian") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Dropdown untuk Anggota
        DynamicDropdownTextField(
            label = "idPeminjaman",
            selectedValue = pengembalianUiState.pengembalianUiEvent.idPeminjaman,
            listItems = peminjamanList.map { it.idPeminjaman },
            onValueChanged = {
                val selectedAnggota = peminjamanList.find { peminjaman -> peminjaman.idPeminjaman == it }
                onPengembalianValueChange(
                    pengembalianUiState.pengembalianUiEvent.copy(
                        namaAnggota = it,
                        idPeminjaman = selectedAnggota?.idPeminjaman.orEmpty()
                    )
                )
            }
        )




        // Tanggal Pengembalian
        OutlinedTextField(
            value = pengembalianUiState.pengembalianUiEvent.tanggaldikembalikan,
            onValueChange = { onPengembalianValueChange(pengembalianUiState.pengembalianUiEvent.copy(tanggaldikembalikan = it)) },
            label = { Text("Tanggal Pengembalian") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Tombol Simpan
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicDropdownTextField(
    label: String,
    selectedValue: String,
    listItems: List<String>,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedValue,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            listItems.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        expanded = false
                        onValueChanged(item)
                    }
                )
            }
        }
    }
}