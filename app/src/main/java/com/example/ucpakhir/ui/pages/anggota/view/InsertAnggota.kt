package com.example.ucpakhir.ui.pages.anggota.view

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
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.example.ucpakhir.ui.PenyediaViewModel
import com.example.ucpakhir.ui.pages.anggota.viewmodel.InsertAnggotaViewModel
import com.example.ucpakhir.ui.pages.anggota.viewmodel.InsertUiEvent
import com.example.ucpakhir.ui.pages.anggota.viewmodel.InsertUiState
import com.example.ucpakhir.ui.customwidget.CostumeTopAppBar
import com.example.ucpakhir.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.launch

object DestinasiInsertAnggota : DestinasiNavigasi {
    override val route = "insertanggota"
    override val titleRes = "Tambah Anggota"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertAnggota(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertAnggotaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiInsertAnggota.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ){innerPadding ->
        EntryBody(
            insertUiState = viewModel.uiState,
            onAnggotaValueChange = viewModel::updateInsertAnggotaState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertAnggota()
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
fun EntryBody(
    insertUiState: InsertUiState,
    onAnggotaValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onAnggotaValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}




@Composable
fun FormInput(
    insertUiEvent: InsertUiEvent,
    onValueChange: (InsertUiEvent) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.idAnggota,
            onValueChange = { onValueChange(insertUiEvent.copy(idAnggota = it)) },
            label = { Text("ID Anggota") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.nama,
            onValueChange = { onValueChange(insertUiEvent.copy(nama = it)) },
            label = { Text("nama anggota") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.email,
            onValueChange = { onValueChange(insertUiEvent.copy(email = it)) },
            label = { Text("email") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertUiEvent.noTelpon,
            onValueChange = { onValueChange(insertUiEvent.copy(noTelpon = it)) },
            label = { Text("no telpon") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

    }
}