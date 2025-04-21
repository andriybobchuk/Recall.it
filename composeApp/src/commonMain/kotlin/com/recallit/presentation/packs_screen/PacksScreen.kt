package com.recallit.presentation.packs_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.recallit.app.koinViewModel
import com.recallit.data.model.Pack
import com.recallit.presentation.component.Toolbars

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PacksScreen(
    viewModel: PacksViewModel = koinViewModel(),
    onPackClick: (packId: Int) -> Unit,
) {
    val packs = viewModel.packs.value
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val tabTitles = listOf("Mine", "Explore")
    var selectedTabIndex by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            Column {
                Toolbars.Medium(
                    title = "Card Packs",
                    scrollBehavior = scrollBehavior
                )
                TabRow(selectedTabIndex = selectedTabIndex) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { Text(title) }
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { },
                content = {
                    Icon(Icons.Default.Add, contentDescription = "Add Pack")
                }
            )
        },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                when (selectedTabIndex) {
                    0 -> MyPacksTab(packs, onPackClick)
                    1 -> ExplorePacksTab(packs, onPackClick)
                }
            }

        }
    )
}

@Composable
fun MyPacksTab(packs: List<Pack>, onPackClick: (packId: Int) -> Unit) {
    Column {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(packs.size) { index ->
                PackCard(pack = packs[index], onCardClick = { onPackClick(packs[index].id) })
            }
        }
    }
}

@Composable
fun ExplorePacksTab(packs: List<Pack>, onPackClick: (packId: Int) -> Unit) {
    Column {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
//            items(packs.size) { index ->
//                PackCard(pack = packs[index], onCardClick = { onPackClick(packs[index].id) })
//            }
        }
    }
}

@Composable
fun PackCard(
    pack: Pack,
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(16.dp),
        onClick = onCardClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = pack.title,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "${pack.cards.size} cards",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
