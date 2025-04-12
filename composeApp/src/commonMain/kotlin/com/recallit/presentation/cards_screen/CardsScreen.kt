package com.recallit.presentation.cards_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.recallit.app.koinViewModel
import com.recallit.data.model.Card
import com.recallit.data.model.Status
import com.recallit.presentation.component.Toolbars
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CardsScreen(
    packId: Int,
    viewModel: CardsViewModel = koinViewModel(packId),
    onBackClick: () -> Unit
) {
    val currentCard by viewModel.currentCard.collectAsState()
    val currentPack by viewModel.currentPack.collectAsState()

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        topBar = {
            Toolbars.Primary(
                title = currentPack?.title?:"",
                showBackButton = true,
                onBackClick = onBackClick,
                scrollBehavior = scrollBehavior
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                val totalCards: Int = currentPack?.cards?.size?:0
                val pagerState = rememberPagerState(pageCount = { totalCards })

                ProgressIndicator(pagerState.currentPage, totalCards)

                Spacer(modifier = Modifier.height(16.dp))

                if (currentCard != null) {
                    LaunchedEffect(pagerState.currentPage) {
                        viewModel.setCurrentCard(pagerState.currentPage)
                    }
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                    ) {
                        FlashCard(
                            card = currentCard!!
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = { viewModel.changeCardStatus(Status.CORRECT) }) {
                        Text(text = "Correct")
                    }
                    Button(onClick = { viewModel.changeCardStatus(Status.WRONG) }) {
                        Text(text = "Wrong")
                    }
                }
            }
        }
    )
}

@Composable
fun ProgressIndicator(
    currentIndex: Int,
    totalCount: Int,
    modifier: Modifier = Modifier
) {
    val progress by remember(currentIndex, totalCount) {
        derivedStateOf {
            if (totalCount <= 1) 1f else currentIndex.toFloat() / (totalCount - 1).toFloat()
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .weight(1f)
                .height(4.dp)
        )
        Text(
            text = "${currentIndex + 1}/$totalCount",
            style = MaterialTheme.typography.bodyLarge,
            modifier = modifier.padding(horizontal = 18.dp)
        )
    }
}

@Composable
fun FlashCard(card: Card) {
    var isFrontSide by remember { mutableStateOf(true) }
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        onClick = { isFrontSide = !isFrontSide },
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (isFrontSide) card.front else card.back,
                style = if (isFrontSide) MaterialTheme.typography.titleLarge else MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}
