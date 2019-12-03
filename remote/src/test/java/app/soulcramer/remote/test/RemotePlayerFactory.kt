package app.soulcramer.remote.test

import app.soulcramer.remote.model.RemotePlayer
import app.soulcramer.remote.test.DataFactory.Factory.randomUuid

class RemotePlayerFactory {

    companion object Factory {

        fun makePlayer(
            id: String = randomUuid(),
            name: String = randomUuid(),
            position: String = randomUuid(),
            singin: String = randomUuid(),
            birthday: String = randomUuid(),
            imageUrl: String = randomUuid()
        ): RemotePlayer {
            return RemotePlayer(id, name, position, singin, birthday, imageUrl)
        }

        fun makePlayerList(
            count: Int,
            name: String = ""
        ): List<RemotePlayer> {
            val teams = mutableListOf<RemotePlayer>()
            repeat(count) {
                teams += makePlayer(name = name)
            }
            return teams
        }
    }
}