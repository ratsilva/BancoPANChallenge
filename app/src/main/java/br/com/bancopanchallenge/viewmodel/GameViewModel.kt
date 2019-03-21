package br.com.bancopanchallenge.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.bancopanchallenge.model.Game
import br.com.bancopanchallenge.model.GameMainRepository
import androidx.databinding.adapters.NumberPickerBindingAdapter.setValue

class GameViewModel : ViewModel(){

    private var repository: GameMainRepository = GameMainRepository()
    lateinit var game: LiveData<Game>

    var name: ObservableField<String> = ObservableField("")
    var viewers: ObservableInt = ObservableInt(0)
    var channels: ObservableInt = ObservableInt(0)


    fun fetchGame(gameName: String) {
        this.game = repository.getGameDB(gameName)

        /*
        Log.v("gameInfo", this.game.name)
        Log.v("gameInfo", ""+this.game.viewers)
        Log.v("gameInfo", ""+this.game.channels)
        Log.v("gameInfo", ""+this.game.logo)

        name.set(this.game.name)
        viewers.set(this.game.viewers)
        channels.set(this.game.channels)
        */


    }

}