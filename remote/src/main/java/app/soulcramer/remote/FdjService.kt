package app.soulcramer.remote

import app.soulcramer.remote.model.ResponseLeague
import app.soulcramer.remote.model.ResponsePlayer
import app.soulcramer.remote.model.ResponseTeam
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Defines the abstract methods used for interacting with the NotifyMoe API
 */
interface FdjService {


    @GET("all_leagues.php")
    suspend fun getLeagues(): ResponseLeague

    @GET("search_all_teams.php")
    suspend fun getLeagueTeams(@Query("l") leagueName: String): ResponseTeam

    @GET("searchplayers.php")
    suspend fun getTeamPlayers(@Query("t") teamName: String): ResponsePlayer
}
