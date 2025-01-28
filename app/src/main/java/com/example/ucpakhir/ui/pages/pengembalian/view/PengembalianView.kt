package com.example.ucpakhir.ui.pages.pengembalian.view


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
import com.example.ucpakhir.model.Pengembalian
import com.example.ucpakhir.ui.PenyediaViewModel
import com.example.ucpakhir.ui.pages.anggota.view.DestinasiAnggota
import com.example.ucpakhir.ui.customwidget.CostumeTopAppBar
import com.example.ucpakhir.ui.pages.pengembalian.viewmodel.PengembalianViewModel
import com.example.ucpakhir.ui.pages.pengembalian.viewmodel.HomeUiState
import com.example.ucpakhir.ui.customwidget.TopBarWithSearch
import com.example.ucpakhir.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.delay

object DestinasiPengembalian : DestinasiNavigasi {
    override val route = "pengembalian"
    override val titleRes = "Halaman Pengembalian"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PengembalianScreen(
    navigateBack: () -> Unit,
    navigateToEntry: () -> Unit,
    navigateToEdit: (String) -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: PengembalianViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    // State untuk dialog konfirmasi
    val showDialog = remember { mutableStateOf(false) }
    val pengembalianToDelete = remember { mutableStateOf<Pengembalian?>(null) }
    val filteredPengembalian = viewModel.filteredPengembalian
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiPengembalian.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {viewModel.getPengembalian()}
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
            // Menampilkan status pengembalian dan daftar pengembalian yang difilter
            PengembalianStatus(
                homeUiState = viewModel.pengembalianUiState,
                retryAction = { viewModel.getPengembalian() },
                modifier = Modifier,
                onDetailClick = onDetailClick,
                onDeleteClick = { pengembalian ->
                    // Menampilkan dialog konfirmasi sebelum menghapus
                    pengembalianToDelete.value = pengembalian
                    showDialog.value = true
                },
                onEditClick = navigateToEdit,
                filteredPengembalian = filteredPengembalian // Kirim filteredPengembalian ke PengembalianStatus
            )
            // Dialog Konfirmasi
            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    title = { Text("Konfirmasi Hapus") },
                    text = { Text("Apakah Anda yakin ingin menghapus data ${pengembalianToDelete.value?.judulBuku}?") },
                    confirmButton = {
                        TextButton(onClick = {
                            pengembalianToDelete.value?.let {
                                viewModel.deletePengembalian(it.idPengembalian)
                                viewModel.getPengembalian()
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
fun PengembalianStatus(
    homeUiState: HomeUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit,
    onDeleteClick: (Pengembalian) -> Unit = {},
    onEditClick: (String) -> Unit = {},
    filteredPengembalian: List<Pengembalian>
) {
    when (homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeUiState.Success -> {
            if (homeUiState.Pengembalian.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Pengembalian")
                }
            } else {
                PengembalianLayout(
                    pengembalian = filteredPengembalian,
                    modifier = modifier.fillMaxSize(),
                    onDetailClick = { onDetailClick(it.idPengembalian) },
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
fun PengembalianLayout(
    pengembalian: List<Pengembalian>,
    modifier: Modifier = Modifier,
    onDetailClick: (Pengembalian) -> Unit,
    onDeleteClick: (Pengembalian) -> Unit = {},
    onEditClick: (String) -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        pengembalian.forEach { pengembalianItem ->
            PengembalianCard(
                pengembalian = pengembalianItem,
                modifier = Modifier.fillMaxWidth().clickable { onDetailClick(pengembalianItem) },
                onDeleteClick = { onDeleteClick(pengembalianItem) },
                onEditClick = { onEditClick(pengembalianItem.idPengembalian) }
            )
        }
    }
}

@Composable
fun PengembalianCard(
    pengembalian: Pengembalian,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pengembalian) -> Unit = {},
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
                    text = "Data Pengembalian",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
                Row(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(onClick = { onEditClick(pengembalian.idPengembalian) }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Pengembalian",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    IconButton(onClick = { onDeleteClick(pengembalian) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Hapus Pengembalian",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TableRow(label = "nama", value = pengembalian.namaAnggota)
                TableRow(label = "Judul", value = pengembalian.judulBuku)

                TableRow(label = "tanggal dikembalikan", value = pengembalian.tanggaldikembalikan)

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