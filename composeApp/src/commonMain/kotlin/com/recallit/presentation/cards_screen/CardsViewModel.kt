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
        _currentPack.value = repository.getPack(packId)
        setCurrentCard(0)
    }

    fun setCurrentCard(index: Int) {
        if (_currentIndex.value < totalCards) {
            _currentCard.value = packCards[index]
        }
    }

    fun changeCardStatus(status: Status) {
        _currentCard.value?.let {
            repository.changeCardStatus(it.id, status)
            _currentCard.value = it.copy(status = status)
        }
    }
}
