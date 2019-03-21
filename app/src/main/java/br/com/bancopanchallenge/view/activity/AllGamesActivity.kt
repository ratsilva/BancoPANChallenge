package br.com.bancopanchallenge.view.activity

import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.bancopanchallenge.R
import br.com.bancopanchallenge.databinding.ActivityAllGamesBinding
import br.com.bancopanchallenge.model.Game
import br.com.bancopanchallenge.model.retrofit.Status
import br.com.bancopanchallenge.view.adapter.AllGamesAdapter
import br.com.bancopanchallenge.viewmodel.AllGamesViewModel
import br.com.bancopanchallenge.view.custom.EndlessRecyclerOnScrollListener
import androidx.recyclerview.widget.RecyclerView

class AllGamesActivity : AppCompatActivity() {

    private lateinit var viewModel: AllGamesViewModel
    private lateinit var binding: ActivityAllGamesBinding

    private val adapter = AllGamesAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get DataBinding from layout
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_games)
        binding.lifecycleOwner = this

        // Get ViewModel for this Viewm
        viewModel = ViewModelProviders.of(this).get(AllGamesViewModel::class.java)

        // Associate ViewModel to DataBinding
        binding.viewModel = viewModel

        // Configure Swipe Layout
        configureSwipeLayout()

        // Configure RecyclerView
        configureRecyclerView()

        // Configure Button to scroll to top of recyclerview
        configureTopButton()

        // Configure Observable fields from ViewModel
        configureObservableFields()

        // Get data (Games) from ViewModel
        viewModel.getAllGamesAPI()


    }

    fun configureSwipeLayout() {

        binding.allgamesSwipelayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            adapter.clearGames()
            viewModel.getAllGamesAPI()
        })

    }

    fun configureRecyclerView(){

        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.orientation = RecyclerView.VERTICAL

        binding.allgamesRecyclerview.layoutManager = gridLayoutManager
        binding.allgamesRecyclerview.adapter = adapter

        binding.allgamesRecyclerview.addOnScrollListener(object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore() {
                viewModel.getNextGamesAPI()
            }
        });

    }

    fun configureTopButton(){

        binding.allgamesTopbutton.setOnClickListener(View.OnClickListener {
            binding.allgamesRecyclerview.smoothScrollToPosition(0)
        })

    }

    fun configureObservableFields(){

        viewModel.getAllGames().observe(this, Observer<List<Game>> { games ->
            adapter.updateGames(games)
            binding.allgamesSwipelayout.isRefreshing = false
        })

        viewModel.getStatusAPI().observe(this, Observer<Status> { status ->

            when(status){

                Status.LOADING -> {
                    binding.allgamesProgressbar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.allgamesProgressbar.visibility = View.GONE
                }
                Status.FAIL -> {
                    binding.allgamesProgressbar.visibility = View.GONE
                    //SHOW ERROR MESSAGE
                }
                else -> {
                    binding.allgamesProgressbar.visibility = View.GONE
                }

            }

        })

    }

}