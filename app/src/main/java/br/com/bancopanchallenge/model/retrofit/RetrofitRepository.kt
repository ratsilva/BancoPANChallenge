package br.com.bancopanchallenge.model.retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.bancopanchallenge.model.Game
import br.com.bancopanchallenge.model.room.RoomRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.util.ArrayList

class RetrofitRepository {

    private val statusAPI = MutableLiveData<Status>()

    private val gameService: GameService
    private val roomRepository: RoomRepository

    private val CLIENTE_ID = "1it3vh6uu37zjp5zroevek2tmgfonr"

    init {
        roomRepository = RoomRepository()
        gameService = RetrofitConfig().getGameService()
    }

    fun getStatusAPI(): LiveData<Status> {
        return statusAPI
    }

    fun getAllGames(offset: Int) {

        val listGames = gameService.getTopGames(CLIENTE_ID, offset)

        listGames.enqueue(object : Callback<ResponseGson> {

            override fun onResponse(call: Call<ResponseGson>, response: Response<ResponseGson>) {

                var games: MutableList<Game> = mutableListOf()

                val gameGson = response.body()
                val listTop = gameGson!!.top

                for (rp in listTop!!) {
                    games.add(Game(rp.viewers, rp.channels, rp.game!!.localized_name!!, rp.game!!.box!!["large"]!!, offset))
                }

                roomRepository.deleteAllGames()
                roomRepository.insertAllGames(games)

            }

            override fun onFailure(call: Call<ResponseGson>, t: Throwable) {
                // tratar algum erro
                Log.v("failure", "" + t.message)
            }
        })


    }

}
