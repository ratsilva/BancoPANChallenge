package br.com.bancopanchallenge.app

import android.app.Application
import androidx.room.Room
import android.content.Context
import android.util.Log
import br.com.bancopanchallenge.model.Game
import br.com.bancopanchallenge.model.room.GameDatabase
import br.com.bancopanchallenge.model.room.RoomRepository

class BancoPANApplication : Application() {

    companion object {
        var database: GameDatabase? = null
    }

    private lateinit var instance: BancoPANApplication

    fun getInstance(): BancoPANApplication? {
        return instance
    }

    fun getAppContext(): Context = instance.applicationContext


    override fun onCreate() {
        super.onCreate()

        instance = this

        database = Room.databaseBuilder(getAppContext(), GameDatabase::class.java, "bancopan_database")
            .build()

    }
}