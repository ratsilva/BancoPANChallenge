package br.com.bancopanchallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.bancopanchallenge.model.Game
import br.com.bancopanchallenge.model.GameMainRepository
import br.com.bancopanchallenge.model.retrofit.Status

class AllGamesViewModel : ViewModel() {

    private var repository: GameMainRepository = GameMainRepository()
    private var offsetGames: Int = 0

    fun getAllGames(): LiveData<List<Game>>{
        return repository.getAllGamesDB()
    }

    fun getStatusAPI(): LiveData<Status>{
        return repository.getStatusAPI()
    }

    fun getAllGamesAPI(){
        offsetGames = 0
        repository.getAllGamesAPI(offsetGames)
        offsetGames += 10
    }

    fun getNextGamesAPI(){
        repository.getAllGamesAPI(offsetGames)
        offsetGames += 10
    }


}