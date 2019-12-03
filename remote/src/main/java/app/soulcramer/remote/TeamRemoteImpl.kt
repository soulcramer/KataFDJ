package app.soulcramer.remote

import app.soulcramer.domain.model.Player
import app.soulcramer.domain.repository.team.TeamRemoteStore
import app.soulcramer.remote.mapper.PlayerEntityMapper
import retrofit2.HttpException
import java.net.UnknownHostException

class TeamRemoteImpl(
    private val service: FdjService,
    private val playerEntityMapper: PlayerEntityMapper
) : TeamRemoteStore {

    override suspend fun getPlayers(teamName: String): List<Player> {
        return try {
            service.getTeamPlayers(teamName).players?.map(playerEntityMapper::mapFromRemote)
                ?: emptyList()
        } catch (httpException: HttpException) {
            // Since as of 2019-12-03 the API to get the players is now reserverd to patreons supporter
            // returning us 404 error
            emptyList()
        } catch (uhe: UnknownHostException) {
            // No Internet
            emptyList()
        }
    }
}