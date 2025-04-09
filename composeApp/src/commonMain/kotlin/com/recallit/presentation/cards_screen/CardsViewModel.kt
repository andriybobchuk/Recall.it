package com.recallit.presentation.cards_screen

import androidx.lifecycle.ViewModel
import com.recallit.data.model.Card
import com.recallit.data.model.Status
import com.recallit.domain.repository.CoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CardsViewModel(private val repository: CoreRepository) : ViewModel() {

    private var _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> get() = _currentIndex

    private var _currentCard = MutableStateFlow<Card?>(null)
    val currentCard: StateFlow<Card?> get() = _currentCard

    init {
        loadNextCard()
    }

    fun loadNextCard() {
        val current = _currentIndex.value
        val packCards = repository.getPackCards(1)
        if (current < packCards.size) {
            _currentCard.value = packCards[current]
        }
    }

    fun loadPreviousCard() {
        if (_currentIndex.value > 0) {
            _currentIndex.value -= 1
            loadNextCard()
        }
    }

    fun flipCard() {
        _currentCard.value?.let {
            _currentCard.value = it.copy(status = if (it.status == Status.CORRECT) Status.WRONG else Status.CORRECT)
        }
    }

    fun changeCardStatus(status: Status) {
        _currentCard.value?.let {
            repository.changeCardStatus(it.id, status)
        }
    }

    fun goToNextCard() {
        _currentIndex.value += 1
        loadNextCard()
    }

    fun goToPreviousCard() {
        if (_currentIndex.value > 0) {
            _currentIndex.value -= 1
            loadNextCard()
        }
    }
}
