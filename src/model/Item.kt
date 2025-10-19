package model

// item class to represent type, damage or heal, and a short description
data class Item(
    val name: String,
    val type: String,
    val damage: Int,
    val heal: Int,
    val description: String = ""

)

