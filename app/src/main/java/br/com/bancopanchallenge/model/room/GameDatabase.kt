package br.com.bancopanchallenge.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.bancopanchallenge.model.Game

@Database(entities = [(Game::class)], version = 1, exportSchema = false)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameDAO(): GameDAO
}