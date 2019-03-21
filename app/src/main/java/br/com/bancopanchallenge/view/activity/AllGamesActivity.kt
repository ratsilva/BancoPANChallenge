package br.com.bancopanchallenge.view.activity

import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.bancopanchallenge.R
import br.com.bancopanchallenge.databinding.ActivityAllGamesBinding
import br.com.bancopanchallenge.view.adapter.AllGamesAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.bancopanchallenge.model.Game
import br.com.bancopanchallenge.model.retrofit.Status
import br.com.bancopanchallenge.viewmodel.AllGamesViewModel
import de.keyboardsurfer.android.widget.crouton.Crouton
import de.keyboardsurfer.android.widget.crouton.Style

class AllGamesActivity : AppCompatActivity() {

    private lateinit var viewModel: AllGamesViewModel
    private lateinit var binding: ActivityAllGamesBinding

    private val adapter = AllGamesAdapter()

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


    }

    fun configureSwipeLayout() {

        binding.allgamesSwipelayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            viewModel.refreshAllGames()
            binding.allgamesSwipelayout.isRefreshing = false
        })

    }

    fun configureRecyclerView(){

        val gridLayoutManager = GridLayoutManager(this, 2)
        gridLayoutManager.orientation = RecyclerView.VERTICAL

        binding.allgamesRecyclerview.layoutManager = gridLayoutManager
        binding.allgamesRecyclerview.adapter = adapter

    }

    fun configureTopButton(){

        binding.allgamesTopbutton.setOnClickListener(View.OnClickListener {
            binding.allgamesRecyclerview.smoothScrollToPosition(0)
        })

    }

    fun configureObservableFields(){

        viewModel.getAllGames().observe(this, Observer<PagedList<Game>> { pagedList ->
            adapter.submitList(pagedList)
        })

        viewModel.getNetworkStatus().observe(this, Observer<Boolean> { status ->
            if(!status){
                Toast.makeText(applicationContext, R.string.no_internet, Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.getLoadingStatus().observe(this, Observer<Boolean> { status ->
            if(status){
                binding.allgamesProgressbar.visibility = View.VISIBLE
            }else{
                binding.allgamesProgressbar.visibility = View.GONE
            }
        })


    }

}