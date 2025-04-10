package com.recallit.presentation.cards_screen

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.recallit.app.koinViewModel
import com.recallit.data.model.Card
import com.recallit.data.model.Status
import com.recallit.presentation.component.Toolbars
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardsScreen(
    packId: Int,
    viewModel: CardsViewModel = koinViewModel(packId),
    onBackClick: () -> Unit
) {
    val currentCard by viewModel.currentCard.collectAsState()
    val currentIndex by viewModel.currentIndex.collectAsState()
    val currentPack by viewModel.currentPack.collectAsState()
    val totalCards: Int = currentPack?.cards?.size?:0

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
                LinearProgressIndicator(
                    progress = {
                        if (currentCard != null && totalCards > 0) {
                            currentIndex.toFloat() / totalCards
                        } else {
                            0f
                        }
                    },
                    modifier = Modifier.padding(16.dp)
                )


                Spacer(modifier = Modifier.height(16.dp))

                if (currentCard != null) {
                    FlipCard(
                        card = currentCard!!,
                        onCardFlip = { viewModel.flipCard() },
                        onSwipeLeft = { viewModel.goToPreviousCard() },
                        onSwipeRight = { viewModel.goToNextCard() }
                    )
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
fun FlipCard(
    card: Card,
    onCardFlip: () -> Unit,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit
) {
    var isFlipped by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    // Swiping left or right to change cards
                    if (dragAmount > 0) {
                        onSwipeLeft()  // Swipe to the left (previous card)
                    } else if (dragAmount < 0) {
                        onSwipeRight()  // Swipe to the right (next card)
                    }
                }
                detectTapGestures(onTap = {
                    isFlipped = !isFlipped
                    onCardFlip() // Trigger the flip action in the ViewModel
                })
            }
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (isFlipped) card.back else card.front,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 24.sp
                )
            }
        }
    }
}
