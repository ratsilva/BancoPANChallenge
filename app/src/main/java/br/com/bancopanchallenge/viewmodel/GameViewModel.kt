package br.com.bancopanchallenge.viewmodel

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import br.com.bancopanchallenge.model.Game

class GameViewModel : ViewModel() {

    var name = ObservableField<String>("")
    var viewers = ObservableInt(0)
    var channels = ObservableInt(0)


    fun fetchGame(game: Game) {

        this.name.set(game.name)
        this.viewers.set(game.viewers)
        this.channels.set(game.channels)

    }

}