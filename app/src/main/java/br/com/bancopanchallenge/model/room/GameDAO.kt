package br.com.bancopanchallenge.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.bancopanchallenge.model.Game

@Dao
interface GameDAO {

    @Query("DELETE FROM Games")
    fun deleteAllGames()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllGames(gameList: List<Game>)

    @Query("SELECT * FROM Games ORDER BY viewers ASC")
    fun getAllGames(): LiveData<List<Game>>

    @Query("SELECT * FROM Games WHERE name = :nameGame")
    fun getGame(nameGame: String): LiveData<Game>

}