package br.com.bancopanchallenge.view.activity

import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.bancopanchallenge.R
import br.com.bancopanchallenge.databinding.ActivityGameBinding
import br.com.bancopanchallenge.viewmodel.GameViewModel

class GameActivity : AppCompatActivity(){

    private lateinit var viewModel: GameViewModel
    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_game)
        binding.lifecycleOwner = this

        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        binding.viewModel = viewModel
        binding.executePendingBindings()


    }

}