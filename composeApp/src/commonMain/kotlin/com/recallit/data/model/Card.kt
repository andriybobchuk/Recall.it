package com.recallit.data.model

data class Card(
    val id: Int,
    val front: String,
    val back: String,
    var status: Status = Status.UNANSWERED
)

enum class Status {
    UNANSWERED, CORRECT, WRONG
}
