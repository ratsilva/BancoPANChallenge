package br.com.bancopanchallenge.model.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig {

    private val BASE_URL = "https://api.twitch.tv/kraken/";

    private var retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getGameService () : GameService{
        return retrofit.create(GameService::class.java)
    }
}