package com.recallit.presentation.cards_screen

import androidx.lifecycle.ViewModel
import com.recallit.data.model.Card
import com.recallit.data.model.Pack
import com.recallit.data.model.Status
import com.recallit.domain.repository.CoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CardsViewModel(
    packId: Int,
    private val repository: CoreRepository
) : ViewModel() {

    private var _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> get() = _currentIndex

    private var _currentCard = MutableStateFlow<Card?>(null)
    val currentCard: StateFlow<Card?> get() = _currentCard

    private var _currentPack = MutableStateFlow<Pack?>(null)
    val currentPack: StateFlow<Pack?> get() = _currentPack

    private val packCards = repository.getPackCards(packId)
    private val totalCards = packCards.size

    init {
        loadNextCard()
    }

    fun loadNextCard() {
        if (_currentIndex.value < totalCards) {
            _currentCard.value = packCards[_currentIndex.value]
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
        if (_currentIndex.value < totalCards - 1) {
            _currentIndex.value += 1
            loadNextCard()
        }
    }

    fun goToPreviousCard() {
        if (_currentIndex.value > 0) {
            _currentIndex.value -= 1
            loadNextCard()
        }
    }

    // For swipe functionality:
    fun onSwipeLeft() {
        goToPreviousCard()
    }

    fun onSwipeRight() {
        goToNextCard()
    }
}
