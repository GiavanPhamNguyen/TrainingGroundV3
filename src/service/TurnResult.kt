package service

//data class responsible for keeping track of results of each turn
data class TurnResult(
    val attacking: String,
    val attacked: String,
    val damage: Int,
    val defeated: Boolean,
    var status: Status = Status.ONGOING,
)

//four possible results for the game
enum class Status {
    ONGOING,
    VICTORY,
    DEFEAT,
    ESCAPED
}