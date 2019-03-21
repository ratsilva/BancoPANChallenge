package br.com.bancopanchallenge.view.activity

import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.bancopanchallenge.R
import br.com.bancopanchallenge.databinding.ActivityGameBinding
import br.com.bancopanchallenge.model.Game
import br.com.bancopanchallenge.viewmodel.GameViewModel
import com.bumptech.glide.Glide

class GameActivity : AppCompatActivity(){

    private lateinit var viewModel: GameViewModel
    private lateinit var binding: ActivityGameBinding

    private lateinit var gameName: String
    private var gameViewers: Int = 0
    private var gameChannels: Int = 0
    private lateinit var gameLogo: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_game)

        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        binding.viewModel = viewModel

        getExtras()
    }

    fun getExtras(){

        var bundle :Bundle ?=intent.extras

        bundle?.let {
            gameName = bundle.getString("name", "")
            gameChannels = bundle.getInt("channels")
            gameViewers = bundle.getInt("viewers")
            gameLogo = bundle.getString("logo", "")
        }

        configureGame(game = Game(
            gameViewers,
            gameChannels,
            gameName,
            gameLogo,
            0
        ))
        configureLogo()

    }

    fun configureGame(game: Game){
        viewModel.fetchGame(game)
    }

    fun configureLogo(){

        Glide
            .with(this)
            .load(gameLogo)
            .fitCenter()
            .placeholder(R.drawable.progress_animation)
            .into(binding.activitygameImage);

    }

}