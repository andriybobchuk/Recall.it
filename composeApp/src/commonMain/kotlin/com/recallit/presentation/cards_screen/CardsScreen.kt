package com.recallit.presentation.cards_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.recallit.app.koinViewModel
import com.recallit.data.model.Card
import com.recallit.data.model.Status
import com.recallit.presentation.component.Toolbars

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
                title = currentPack?.title ?: "",
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
                val totalCards: Int = currentPack?.cards?.size ?: 0
                val pagerState = rememberPagerState(pageCount = { totalCards })

                ProgressIndicator(pagerState.currentPage, totalCards)

                Spacer(modifier = Modifier.height(20.dp))

                CardStatusIndicatorRow(
                    learningCount = currentPack?.cards?.count { it.status == Status.WRONG },
                    unansweredCount = currentPack?.cards?.count { it.status == Status.UNANSWERED },
                    rightCount = currentPack?.cards?.count { it.status == Status.CORRECT },
                    true
                )

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

                AnswerControls(
                    onWrongAnswerClick = { viewModel.changeCardStatus(Status.WRONG) },
                    onCorrectAnswerClick = { viewModel.changeCardStatus(Status.CORRECT) }
                )
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
fun CardStatusIndicatorRow(
    learningCount: Int?,
    unansweredCount: Int?,
    rightCount: Int?,
    hideLabels: Boolean = false,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatusItem(color = MaterialTheme.colorScheme.error, label = "Wrong", count = learningCount, hideLabel = hideLabels)
        StatusItem(
            color = MaterialTheme.colorScheme.outline.copy(.7f),
            label = "Reviewing",
            count = unansweredCount,
            hideLabel = hideLabels
        )
        StatusItem(color = MaterialTheme.colorScheme.primary, label = "Right", count = rightCount, hideLabel = hideLabels)
    }
}

@Composable
private fun StatusItem(
    color: Color,
    label: String,
    count: Int?,
    hideLabel: Boolean = false,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(color)
        )
        if (!hideLabel) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
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
                fontWeight = if (isFrontSide) FontWeight.Bold else FontWeight.Normal,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(32.dp),
            )
        }
    }
}

@Composable
fun AnswerControls(
    onCorrectAnswerClick: () -> Unit,
    onWrongAnswerClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = onWrongAnswerClick) {
            Text(text = "Wrong")
        }
        Button(onClick = onCorrectAnswerClick) {
            Text(text = "Correct")
        }
    }
}
