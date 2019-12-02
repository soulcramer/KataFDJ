package app.soulcramer.presentation.test

import app.soulcramer.domain.model.Player
import app.soulcramer.presentation.test.DataFactory.Factory.randomUuid

/**
 * Factory class for Player related instances
 */
class PlayerFactory {

    companion object Factory {

        fun makePlayer(
            id: String = randomUuid(),
            name: String = randomUuid(),
            position: String = randomUuid(),
            singin: String = randomUuid(),
            imageUrl: String = randomUuid()
        ): Player {
            return Player(id, name, position, singin, imageUrl)
        }

        fun makePlayerList(
            count: Int,
            name: String = ""
        ): List<Player> {
            val teams = mutableListOf<Player>()
            repeat(count) {
                teams += makePlayer(name = name)
            }
            return teams
        }
    }
}