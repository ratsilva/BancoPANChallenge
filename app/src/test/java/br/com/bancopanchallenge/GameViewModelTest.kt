package br.com.bancopanchallenge

import br.com.bancopanchallenge.model.Game
import br.com.bancopanchallenge.viewmodel.GameViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GameViewModelTest {

    private lateinit var gameViewModel: GameViewModel

    @Before
    fun setup() {
        gameViewModel = GameViewModel()
    }

    @Test
    fun testFetchGame(){

        var game: Game = Game(100, 200, "GTA", "logo.jpg", 0)
        gameViewModel.fetchGame(game)

        Assert.assertEquals(100, gameViewModel.viewers.get())
        Assert.assertEquals(200, gameViewModel.channels.get())
        Assert.assertEquals("GTA", gameViewModel.name.get())
    }

    @Test
    fun testInitValues(){

        Assert.assertEquals(0, gameViewModel.viewers.get())
        Assert.assertEquals(0, gameViewModel.channels.get())
        Assert.assertEquals("", gameViewModel.name.get())
    }

}
