package com.example.ucpakhir.ui.pages.peminjaman.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucpakhir.R
import com.example.ucpakhir.model.Peminjaman
import com.example.ucpakhir.ui.PenyediaViewModel
import com.example.ucpakhir.ui.pages.anggota.view.DestinasiAnggota
import com.example.ucpakhir.ui.customwidget.CostumeTopAppBar
import com.example.ucpakhir.ui.pages.peminjaman.viewmodel.PeminjamanViewModel
import com.example.ucpakhir.ui.pages.peminjaman.viewmodel.HomeUiState
import com.example.ucpakhir.ui.customwidget.TopBarWithSearch
import com.example.ucpakhir.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.delay

object DestinasiPeminjaman : DestinasiNavigasi {
    override val route = "peminjaman"
    override val titleRes = "Halaman Peminjaman"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeminjamanScreen(
    navigateBack: () -> Unit,
    navigateToEntry: () -> Unit,
    navigateToEdit: (String) -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: PeminjamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    // State untuk dialog konfirmasi
    val showDialog = remember { mutableStateOf(false) }
    val peminjamanToDelete = remember { mutableStateOf<Peminjaman?>(null) }
    val filteredPeminjaman = viewModel.filteredPeminjaman
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiAnggota.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {viewModel.getPeminjaman()}
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Kontak")
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Menampilkan status peminjaman dan daftar peminjaman yang difilter
            PeminjamanStatus(
                homeUiState = viewModel.peminjamanUiState,
                retryAction = { viewModel.getPeminjaman() },
                modifier = Modifier,
                onDetailClick = onDetailClick,
                onDeleteClick = { peminjaman ->
                    // Menampilkan dialog konfirmasi sebelum menghapus
                    peminjamanToDelete.value = peminjaman
                    showDialog.value = true
                },
                onEditClick = navigateToEdit,
                filteredPeminjaman = filteredPeminjaman // Kirim filteredPeminjaman ke PeminjamanStatus
            )
            // Dialog Konfirmasi
            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    title = { Text("Konfirmasi Hapus") },
                    text = { Text("Apakah Anda yakin ingin menghapus data ${peminjamanToDelete.value?.judulBuku}?") },
                    confirmButton = {
                        TextButton(onClick = {
                            peminjamanToDelete.value?.let {
                                viewModel.deletePeminjaman(it.idPeminjaman)
                                viewModel.getPeminjaman()
                            }
                            showDialog.value = false
                        }) {
                            Text("Hapus")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog.value = false }) {
                            Text("Batal")
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun PeminjamanStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
    onDeleteClick: (Peminjaman) -> Unit = {},
    onEditClick: (String) -> Unit = {},
    filteredPeminjaman: List<Peminjaman>
) {
    when (homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiState.Success -> {
            if (homeUiState.Peminjaman.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Peminjaman")
                }
            } else {
                PeminjamanLayout(
                    peminjaman = filteredPeminjaman,
                    modifier = modifier.fillMaxSize(),
                    onDetailClick = { onDetailClick(it.idPeminjaman) },
                    onDeleteClick = { onDeleteClick(it) },
                    onEditClick = onEditClick
                )
            }
        }
        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier, duration: Long = 1000000000L) {
    LaunchedEffect(Unit) {
        delay(duration)
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.loadingg),
            contentDescription = stringResource(R.string.loading)
        )
        Text(
            text = "loading",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.loading),
            contentDescription = stringResource(R.string.loading_failed)
        )
        Text(
            text = "Data gagal dimuat",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun PeminjamanLayout(
    peminjaman: List<Peminjaman>,
    modifier: Modifier = Modifier,
    onDetailClick: (Peminjaman) -> Unit,
    onDeleteClick: (Peminjaman) -> Unit = {},
    onEditClick: (String) -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        peminjaman.forEach { peminjamanItem ->
            PeminjamanCard(
                peminjaman = peminjamanItem,
                modifier = Modifier.fillMaxWidth().clickable { onDetailClick(peminjamanItem) },
                onDeleteClick = { onDeleteClick(peminjamanItem) },
                onEditClick = { onEditClick(peminjamanItem.idPeminjaman) }
            )
        }
    }
}

@Composable
fun PeminjamanCard(
    peminjaman: Peminjaman,
    modifier: Modifier = Modifier,
    onDeleteClick: (Peminjaman) -> Unit = {},
    onEditClick: (String) -> Unit = {}
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(Color(0xFFEDEDED))
            .border(1.dp, Color.Gray, RectangleShape)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFB771E5),
                                Color(0xFF8B5DFF)
                            )
                        )
                    )
                    .padding(8.dp)
            ) {
                Text(
                    text = "Data Peminjaman",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Row(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(onClick = { onEditClick(peminjaman.idPeminjaman) }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Peminjaman",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    IconButton(onClick = { onDeleteClick(peminjaman) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Hapus Peminjaman",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TableRow(label = "nama", value = peminjaman.namaAnggota)
                TableRow(label = "Judul", value = peminjaman.judulBuku)

                TableRow(label = "tanggal peminjaman", value = peminjaman.tanggalPeminjaman)
                TableRow(label = "tanggal pengembalian", value = peminjaman.tanggalPengembalian)
            }
        }
    }
}


@Composable
fun TableRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(2f)
        )
    }
}