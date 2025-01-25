package com.example.ucpakhir.ui.pages.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ucpakhir.R
import com.example.ucpakhir.ui.navigation.DestinasiNavigasi

object DestinasiHome : DestinasiNavigasi {
    override val route = "home"
    override val titleRes = "Home Mhs"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToBuku: () -> Unit,
    navigateToAnggota: () -> Unit,
    navigateToPeminjaman: () -> Unit,
    navigateToPengembalian: () -> Unit,
    navigateToAddBuku: () -> Unit,
    navigateToAddAnggota: () -> Unit,
    navigateToAddPeminjaman: () -> Unit,
    navigateToAddPengembalian: () -> Unit
) {
    Scaffold(
        topBar = {
            TopHeader()

        },
        bottomBar = {
            BottomNavigationBar()
        }

    ) { paddingvalues ->
        Column(modifier = Modifier.padding(paddingvalues)) {
            Row(
            ) {
                MenuSectionAdd(
                    navigateToAddBuku = navigateToAddBuku,
                    navigateToAddAnggota = navigateToAddAnggota,
                    navigateToAddPeminjaman = navigateToAddPeminjaman,
                    navigateToAddPengembalian = navigateToAddPengembalian,

                )

            }
            MenuSection(
                navigateToBuku = navigateToBuku,
                navigateToAnggota = navigateToAnggota,
                navigateToPeminjaman = navigateToPeminjaman,
                navigateToPengembalian = navigateToPengembalian,

            )
        }
    }
}


@Composable
fun TopHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(shape = RoundedCornerShape(bottomStart = 60.dp, bottomEnd = 60.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFB771E5),
                        Color(0xFF8B5DFF)
                    )
                )
            )
            .padding(16.dp),
    ) {
        // Kartu utama
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)

                .padding(16.dp)
        ) {

            // Konten di dalam kartu
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Foto profil di sisi kanan
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFCBD5E1))
                        .border(2.dp, Color.White, CircleShape),
                    contentScale = ContentScale.Crop
                )
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Text(
                        text = "Card member",

                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White

                        )
                    )
                    Spacer(modifier = Modifier.padding(1.dp))
                    Text(
                        text = "NAMA PESERTA: MIFTAHUL HUDA ",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White

                        )
                    )
                    Text(
                        text = "PEKERJAAN: MARINIR",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                    Text(
                        text = "Nomor: 1234 5678 9012 3456",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White

                        )
                    )

                }

            }
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .offset(y = 120.dp)) {
            SearchBar()
        }
    }
}


@Composable
fun SearchBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFE9E3F3)),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier
                .padding(horizontal = 1.dp)
                .fillMaxWidth(),
            textStyle = TextStyle(fontSize = 16.sp, color = Color.Gray),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box { innerTextField() }
                }
            }
        )
    }
}

@Composable
fun MenuSectionAdd(
    navigateToAddBuku: () -> Unit,
    navigateToAddAnggota: () -> Unit,
    navigateToAddPeminjaman: () -> Unit,
    navigateToAddPengembalian: () -> Unit,
){
    Column (modifier = Modifier.fillMaxWidth()){
        Text(
            text = "Menu Tambah",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black
            ),
            modifier = Modifier.padding(16.dp)
        )
        LazyHorizontalGrid (
            rows = GridCells.Fixed(1),
            modifier = Modifier.fillMaxHeight(0.3f).fillMaxWidth(),

            ) {
            item {
                GradientCardAdd(
                    title = "Book",
                    iconResId = R.drawable.book, // Ganti dengan ID drawable Anda
                    gradient = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFB771E5),
                            Color(0xFF8B5DFF)
                        )
                    ),
                    onClick = navigateToAddBuku
                )
            }
            item {
                GradientCardAdd(
                    title = "Member",
                    iconResId = R.drawable.boy, // Ganti dengan ID drawable Anda
                    gradient = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFB771E5),
                            Color(0xFF8B5DFF)
                        )
                    ),
                    onClick = navigateToAddAnggota
                )
            }
            item {
                GradientCardAdd(
                    title = "Borrow",
                    iconResId = R.drawable.peminjaman, // Ganti dengan ID drawable Anda
                    gradient = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFB771E5),
                            Color(0xFF8B5DFF)
                        )
                    ),
                    onClick = navigateToAddPeminjaman
                )
            }
            item {
                GradientCardAdd(
                    title = "return",
                    iconResId = R.drawable.pengembalian, // Ganti dengan ID drawable Anda
                    gradient = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFB771E5),
                            Color(0xFF8B5DFF)
                        )
                    ),
                    onClick = navigateToAddPengembalian
                )
            }
        }
    }

}


@Composable
fun GradientCardAdd(
    title: String,
    iconResId: Int, // Parameter untuk ID drawable
    gradient: Brush,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var clicked by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (clicked) 0.98f else 1f,
        animationSpec = tween(durationMillis = 150)
    )

    Card(
        modifier = modifier
            .padding(8.dp)
            .clickable {
                clicked = !clicked
                onClick()
            }
            .graphicsLayer(scaleX = scale, scaleY = scale)
            ,
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.elevatedCardElevation(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row (
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = iconResId), // Memuat ikon dari drawable
                    contentDescription = title,
                    modifier = Modifier.size(50.dp),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}

@Composable
fun MenuSection(
    navigateToBuku: () -> Unit,
    navigateToAnggota: () -> Unit,
    navigateToPeminjaman: () -> Unit,
    navigateToPengembalian: () -> Unit,

) {
    Column (modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Menu Utama",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black
            ),
            modifier = Modifier.padding(16.dp)
        )}
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier

                .fillMaxWidth()
                ,
            contentPadding = PaddingValues(8.dp)
        ) {
            item {
                GradientCard(
                    title = "Book",
                    iconResId = R.drawable.book, // Ganti dengan ID drawable Anda
                    gradient = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFB771E5),
                            Color(0xFF8B5DFF)
                        )
                    ),
                    onClick = navigateToBuku
                )
            }
            item {
                GradientCard(
                    title = "Member",
                    iconResId = R.drawable.boy, // Ganti dengan ID drawable Anda
                    gradient = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFB771E5),
                            Color(0xFF8B5DFF)
                        )
                    ),
                    onClick = navigateToAnggota
                )
            }
            item {
                GradientCard(
                    title = "Borrow",
                    iconResId = R.drawable.peminjaman, // Ganti dengan ID drawable Anda
                    gradient = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFB771E5),
                            Color(0xFF8B5DFF)
                        )
                    ),
                    onClick = navigateToPeminjaman
                )
            }
            item {
                GradientCard(
                    title = "return",
                    iconResId = R.drawable.pengembalian, // Ganti dengan ID drawable Anda
                    gradient = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFB771E5),
                            Color(0xFF8B5DFF)
                        )
                    ),
                    onClick = navigateToPengembalian
                )
            }


        }
}


@Composable
fun GradientCard(
    title: String,
    iconResId: Int, // Parameter untuk ID drawable
    gradient: Brush,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var clicked by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (clicked) 0.98f else 1f,
        animationSpec = tween(durationMillis = 150)
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                clicked = !clicked
                onClick()
            }
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .height(80.dp),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.elevatedCardElevation(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = iconResId), // Memuat ikon dari drawable
                    contentDescription = title,
                    modifier = Modifier.size(50.dp),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}



@Composable
fun BottomNavigationBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .offset(y = (-10).dp)
            .clip(RoundedCornerShape(16.dp))

            .border(2.dp, Color.Black, RoundedCornerShape(20.dp))
            .shadow(19.dp, RoundedCornerShape(13.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp), // Memberi padding internal agar isi tidak terlalu sempit
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* Navigate to Home */ }) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home",
                    tint = Color.Black
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = "Schedules List",
                    tint = Color.Black
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Doctor",
                    tint = Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        navigateToBuku = {},
        navigateToAnggota = {},
        navigateToPeminjaman = {},
        navigateToPengembalian = {},
        navigateToAddBuku = {},
        navigateToAddAnggota = {},
        navigateToAddPeminjaman = {},
        navigateToAddPengembalian = {}
    )
}

