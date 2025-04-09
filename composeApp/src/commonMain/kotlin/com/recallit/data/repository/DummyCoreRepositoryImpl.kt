package com.recallit.data.repository

import com.recallit.data.model.Card
import com.recallit.data.model.Pack
import com.recallit.data.model.Status
import com.recallit.domain.repository.CoreRepository

class DummyCoreRepositoryImpl : CoreRepository {

    private val packs = listOf(
        Pack(
            id = 1,
            title = "Math Pack",
            cards = listOf(
                Card(id = 1, front = "What is 2+2?", back = "4"),
                Card(id = 2, front = "What is 3+5?", back = "8")
            )
        ),
        Pack(
            id = 2,
            title = "History Pack",
            cards = listOf(
                Card(id = 3, front = "Who was the first President of the USA?", back = "George Washington"),
                Card(id = 4, front = "What year did World War II end?", back = "1945")
            )
        ),
        Pack(
            id = 3,
            title = "Roman Empire",
            cards = listOf(
                Card(id = 5, front = "Who was the first President of the USA?", back = "George Washington"),
                Card(id = 6, front = "What year did World War II end?", back = "1945")
            )
        )
    )

    override fun getPacks(): List<Pack> {
        return packs
    }

    override fun getPackCards(packId: Int): List<Card> {
        return packs.find { it.id == packId }?.cards ?: emptyList()
    }

    override fun changeCardStatus(cardId: Int, status: Status) {
        val card = packs.flatMap { it.cards }.find { it.id == cardId }
        card?.status = status
    }
}
