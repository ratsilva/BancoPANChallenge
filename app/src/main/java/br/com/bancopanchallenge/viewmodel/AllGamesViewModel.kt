package br.com.bancopanchallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import br.com.bancopanchallenge.model.Game
import androidx.paging.PagedList
import br.com.bancopanchallenge.app.BancoPANApplication
import br.com.bancopanchallenge.model.paging.GameBoundaryCallback
import br.com.bancopanchallenge.model.retrofit.RetrofitRepository
import br.com.bancopanchallenge.model.room.RoomRepository


class AllGamesViewModel : ViewModel() {

    val roomRepository = RoomRepository()

    private val networkState: MutableLiveData<Boolean> = MutableLiveData()

    private var liveData: LiveData<PagedList<Game>>

    private var config: PagedList.Config
    private var livePageListBuilder: LivePagedListBuilder<Int, Game>
    private var bcb: GameBoundaryCallback

    init {

        networkState.postValue(checkInternetConnection())

        config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()

        bcb = GameBoundaryCallback(roomRepository)

        livePageListBuilder = LivePagedListBuilder<Int, Game>(
            roomRepository.getAllGames(),
            config)

        livePageListBuilder.setBoundaryCallback(bcb)

        liveData = livePageListBuilder.build()


    }

    fun getAllGames(): LiveData<PagedList<Game>>{
        return liveData
    }

    fun getNetworkStatus(): LiveData<Boolean>{
        return networkState
    }

    fun getLoadingStatus(): LiveData<Boolean>{
        return bcb.getLoadingStatus()
    }

    fun refreshAllGames(){

        networkState.postValue(checkInternetConnection())

        if(checkInternetConnection()){
            roomRepository.deleteAllGames()
            bcb.onZeroItemsLoaded()
        }
    }

    fun checkInternetConnection(): Boolean{
        return BancoPANApplication.isInternetOn()
    }



}