data class Player(val name: String, val ability: Int, val lucky: Int) {
    init {
        if (this.ability !in 0..100) {
            throw java.lang.Exception("Not in range number of ability")
        }
    }

}

class Tournament(private var players: MutableList<Player>, private val rounds: Int) {
    fun start(): Tournament {
        for (i in 0..rounds) {
            val _result = mutableListOf<Player>()
            players.forEachIndexed { index, player ->
                if (players.size > index + 2) {
                    if (index == 0)
                        _result.add(confrontation(players[index], players[index + 1]))
                    else
                        _result.add(confrontation(players[index + 1], players[index + 2]))
                } else if (players.size % 2 != 0) {
                    _result.add(player)
                }
            }
            if (_result.size > 0)
                players = _result
        }
        return this
    }

    fun result(): Player {
        if (players.size == 2)
            return confrontation(players[0], players[1])
        return players[0]
    }

    private fun confrontation(x: Player, y: Player): Player {
        if (x.ability > y.ability || x.ability == y.ability && x.lucky > y.lucky) {
            return x
        }
        return y
    }
}

// Start Tournament
val players = arrayListOf(
    Player("Luis", 11, 3),
    Player("Pepe", 11, 5),
    Player("Jose", 2, 1),
    Player("Maria", 12, 1),
    Player("Alberto", 11, 1),
    Player("Juan", 11, 2)
)

const val rounds = 2

val winner = Tournament(players, rounds).start().result()

//Log.d("Tournament", "${winner.name}")