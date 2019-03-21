package br.com.bancopanchallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import br.com.bancopanchallenge.model.Game
import androidx.paging.PagedList
import br.com.bancopanchallenge.model.paging.GameBoundaryCallback
import br.com.bancopanchallenge.model.room.RoomRepository


class AllGamesViewModel : ViewModel() {

    val databaseRepository = RoomRepository()

    private var liveData: LiveData<PagedList<Game>>

    private val config: PagedList.Config
    private val livePageListBuilder: LivePagedListBuilder<Int, Game>

    init {

        config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()

        livePageListBuilder = LivePagedListBuilder<Int, Game>(databaseRepository.getAllGames(), config)
        livePageListBuilder.setBoundaryCallback(GameBoundaryCallback(databaseRepository))

        liveData = livePageListBuilder.build()

    }

    fun getAllGames(): LiveData<PagedList<Game>>{
        return liveData
    }

    fun refreshAllGames(){
        liveData = livePageListBuilder.build()
    }

    /*
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
    */


}