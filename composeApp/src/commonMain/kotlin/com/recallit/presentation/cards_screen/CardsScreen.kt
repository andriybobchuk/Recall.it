package com.recallit.presentation.cards_screen

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardsScreen(
    viewModel: CardsViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {
    val currentCard by viewModel.currentCard.collectAsState()
    val currentIndex by viewModel.currentIndex.collectAsState()

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        topBar = {
            Toolbars.Primary(
                title = "Roman Empire",
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
                    progress = if (currentCard != null) currentIndex.toFloat() / 10f else 0f, // Dummy max 10
                    modifier = Modifier.padding(16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (currentCard != null) {
                    FlipCard(currentCard!!)
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
fun FlipCard(card: Card) {
    var isFlipped by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    isFlipped = !isFlipped
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
