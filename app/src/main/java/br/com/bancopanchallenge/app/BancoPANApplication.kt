package br.com.bancopanchallenge.app

import android.app.Application
import androidx.room.Room
import android.content.Context
import br.com.bancopanchallenge.model.room.GameDatabase
import android.net.ConnectivityManager



class BancoPANApplication : Application() {

    companion object {
        var database: GameDatabase? = null
        lateinit var instance: BancoPANApplication

        fun getAppContext(): Context = instance.applicationContext

        fun isInternetOn(): Boolean {

            val cm = getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected

        }
    }


    fun getInstance(): BancoPANApplication? {
        return instance
    }


    override fun onCreate() {
        super.onCreate()

        instance = this

        database = Room.databaseBuilder(getAppContext(), GameDatabase::class.java, "bancopan_database")
            .build()

    }
}