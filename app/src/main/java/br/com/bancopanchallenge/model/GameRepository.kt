package br.com.bancopanchallenge.model

import androidx.lifecycle.LiveData
import androidx.paging.DataSource

interface GameRepository {
    fun getGame(name: String): LiveData<Game>
    fun getAllGames(): DataSource.Factory<Int, Game>
    fun insertAllGames(listGames: List<Game>)
    fun deleteAllGames()
    fun getQtdGames(): Int
}