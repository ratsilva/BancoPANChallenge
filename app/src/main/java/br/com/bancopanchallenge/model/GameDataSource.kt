package br.com.bancopanchallenge.model

import android.util.Log
import androidx.paging.PageKeyedDataSource
import br.com.bancopanchallenge.model.retrofit.*
import br.com.bancopanchallenge.model.room.RoomRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class GameDataSource : PageKeyedDataSource<String, Game>() {

    private val CLIENTE_ID = "1it3vh6uu37zjp5zroevek2tmgfonr"
    private val gameService = RetrofitConfig().getGameService()

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, Game>) {

        val listGames = gameService.getTopGames(CLIENTE_ID)

        listGames.enqueue(object : Callback<ResponseGson> {

            override fun onResponse(call: Call<ResponseGson>, response: Response<ResponseGson>) {

                var game: Game? = null
                val listGames = ArrayList<Game>()

                val gameGson = response.body()
                val listTop = gameGson!!.top

                for (rp in listTop!!) {
                    game = Game(rp.viewers, rp.channels, rp.game!!.localized_name!!, rp.game!!.box!!["large"]!!)
                    listGames.add(game)
                }

                //roomRepository.deleteAllGames()
                //roomRepository.insertAllGames(listGames)


            }

            override fun onFailure(call: Call<ResponseGson>, t: Throwable) {
                // tratar algum erro
                Log.v("failure", "" + t.message)
            }
        })


    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Game>) {


    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Game>) {

    }

}