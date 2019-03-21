package br.com.bancopanchallenge.model

import androidx.lifecycle.LiveData
import br.com.bancopanchallenge.model.retrofit.RetrofitRepository
import br.com.bancopanchallenge.model.retrofit.Status
import br.com.bancopanchallenge.model.room.RoomRepository

class GameMainRepository {

    private val roomRepository: RoomRepository
    private val retrofitRepository: RetrofitRepository

    init {
        roomRepository = RoomRepository()
        retrofitRepository = RetrofitRepository()
    }

    fun getGameDB(name: String): LiveData<Game> {
        return roomRepository.getGame(name)
    }

    fun getAllGamesDB(): LiveData<List<Game>> {
        return roomRepository.getAllGames()
    }

    fun getStatusAPI(): LiveData<Status> {
        return retrofitRepository.statusAPI
    }

    fun getAllGamesAPI(offset: Int) {
        retrofitRepository.getAllGames(offset)
    }

}
