package br.com.bancopanchallenge.model.paging

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import br.com.bancopanchallenge.model.Game
import br.com.bancopanchallenge.model.retrofit.ResponseGson
import br.com.bancopanchallenge.model.retrofit.RetrofitConfig
import br.com.bancopanchallenge.model.room.RoomRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

class GameBoundaryCallback(private val db: RoomRepository) :

    PagedList.BoundaryCallback<Game>() {

    private val CLIENTE_ID = "1it3vh6uu37zjp5zroevek2tmgfonr"

    private val api = RetrofitConfig().getGameService()
    private val executor = Executors.newSingleThreadExecutor()
    private val helper = PagingRequestHelper(executor)

    private val LoadingState: MutableLiveData<Boolean> = MutableLiveData()

    init {
        LoadingState.postValue(false)
    }

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()

        LoadingState.postValue(true)

        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) { helperCallback ->
            api.getTopGames(clientId = CLIENTE_ID, offset = 0)
                .enqueue(object : Callback<ResponseGson> {

                    override fun onFailure(call: Call<ResponseGson>?, t: Throwable) {
                        Log.e("GameBoundaryCallback", "Failed to load data!")
                        helperCallback.recordFailure(t)
                    }

                    override fun onResponse(
                        call: Call<ResponseGson>?,
                        response: Response<ResponseGson>
                    ) {

                        var listGames: MutableList<Game> = mutableListOf()

                        var game: Game
                        val gameGson = response.body()
                        val listTop = gameGson!!.top

                        listTop?.let {
                            for (rp in it) {
                                game = Game(
                                    rp.viewers,
                                    rp.channels,
                                    rp.game!!.localized_name!!,
                                    rp.game!!.box!!.get("large")!!,
                                    0
                                )
                                listGames.add(game)
                            }
                        }

                        executor.execute {
                            db.deleteAllGames()
                            db.insertAllGames(listGames)
                            helperCallback.recordSuccess()
                            LoadingState.postValue(false)
                        }
                    }
                })
        }

    }

    override fun onItemAtEndLoaded(itemAtEnd: Game) {
        super.onItemAtEndLoaded(itemAtEnd)

        LoadingState.postValue(true)

        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) { helperCallback ->
            api.getTopGames(clientId = CLIENTE_ID, offset = (itemAtEnd.offset + 10))
                .enqueue(object : Callback<ResponseGson> {

                    override fun onFailure(call: Call<ResponseGson>?, t: Throwable) {
                        Log.e("GameBoundaryCallback", "Failed to load data!")
                        helperCallback.recordFailure(t)
                    }

                    override fun onResponse(
                        call: Call<ResponseGson>?,
                        response: Response<ResponseGson>
                    ) {

                        var listGames: MutableList<Game> = mutableListOf()

                        var game: Game
                        val gameGson = response.body()
                        val listTop = gameGson!!.top

                        listTop?.let {
                            for (rp in it) {
                                game = Game(
                                    rp.viewers,
                                    rp.channels,
                                    rp.game!!.localized_name!!,
                                    rp.game!!.box!!.get("large")!!,
                                    itemAtEnd.offset + 10
                                )
                                listGames.add(game)
                            }
                        }

                        executor.execute {
                            db.insertAllGames(listGames)
                            helperCallback.recordSuccess()
                            LoadingState.postValue(false)
                        }
                    }
                })
        }

    }

    fun getLoadingStatus() = LoadingState
}
