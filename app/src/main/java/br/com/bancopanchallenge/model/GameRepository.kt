package br.com.bancopanchallenge.model

import androidx.lifecycle.LiveData

interface GameRepository {
    fun getGame(name: String): LiveData<Game>
    fun getAllGames(): LiveData<List<Game>>
    fun insertAllGames(listGames: List<Game>)
    fun deleteAllGames()
}