package br.com.bancopanchallenge.model.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

public interface GameService{

    @GET("games/top")
    fun getTopGames(@Query("client_id") clientId: String,
                    @Query("offset") offset: Int): Call<ResponseGson>

}