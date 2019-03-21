package br.com.bancopanchallenge.model.room

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import br.com.bancopanchallenge.app.BancoPANApplication
import br.com.bancopanchallenge.model.Game
import br.com.bancopanchallenge.model.GameRepository

class RoomRepository : GameRepository{

  private val gameDao: GameDAO = BancoPANApplication.database!!.gameDAO()

  override fun getGame(name: String): LiveData<Game> {
    return gameDao.getGame(name)
  }

  override fun getAllGames(): LiveData<List<Game>> {
    return gameDao.getAllGames()
  }

  override fun insertAllGames(listGames: List<Game>) {
    InsertAsyncTask(gameDao).execute(listGames)
  }

  override fun deleteAllGames() {
    DeleteAsyncTask(gameDao).execute()
  }

  private class InsertAsyncTask internal constructor(private val dao: GameDAO) : AsyncTask<List<Game>, Void, Void>() {
    override fun doInBackground(vararg params: List<Game>): Void? {
      dao.insertAllGames(params[0])
      return null
    }
  }

  private class DeleteAsyncTask internal constructor(private val dao: GameDAO) : AsyncTask<Game, Void, Void>() {
    override fun doInBackground(vararg params: Game): Void? {
      dao.deleteAllGames()
      return null
    }
  }

}