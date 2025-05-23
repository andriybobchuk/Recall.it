package com.recallit.domain.repository

import com.recallit.data.model.Card
import com.recallit.data.model.Pack
import com.recallit.data.model.Status

interface CoreRepository {
    fun getPacks(): List<Pack>
    fun getPack(id: Int): Pack?
    @Deprecated("Use getPack() instead")
    fun getPackCards(packId: Int): List<Card>
    fun changeCardStatus(cardId: Int, status: Status)
}
