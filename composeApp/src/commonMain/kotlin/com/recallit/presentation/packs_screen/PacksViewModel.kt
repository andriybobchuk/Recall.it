package com.recallit.presentation.packs_screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recallit.data.model.Pack
import com.recallit.domain.repository.CoreRepository
import kotlinx.coroutines.launch

class PacksViewModel(private val repository: CoreRepository) : ViewModel() {

    var packs = mutableStateOf<List<Pack>>(emptyList())
        private set

    init {
        fetchPacks()
    }

    private fun fetchPacks() {
        viewModelScope.launch {
            packs.value = repository.getPacks()
        }
    }
}
