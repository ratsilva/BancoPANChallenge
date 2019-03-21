package br.com.bancopanchallenge.model.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import br.com.bancopanchallenge.model.Game

@Dao
interface GameDAO {

    @Query("DELETE FROM Games")
    fun deleteAllGames()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllGames(gameList: List<Game>)

    @Query("SELECT * FROM Games ORDER BY `offset` ASC")
    fun getAllGames(): DataSource.Factory<Int, Game>

    @Query("SELECT * FROM Games WHERE name = (:nameGame)")
    fun getGame(nameGame: String): LiveData<Game>

    @Query("SELECT Count(name) FROM Games")
    fun getQtdGames(): Int

}