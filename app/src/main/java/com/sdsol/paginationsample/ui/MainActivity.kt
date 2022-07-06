package com.sdsol.paginationsample.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.sdsol.paginationsample.databinding.ActivityMainBinding
import com.sdsol.paginationsample.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var sessionAdapter: SessionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionAdapter = SessionAdapter()
        setupUI()
        setupObservers()
        viewModel.getSessions()

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            sessionAdapter.refresh()
        }
    }

    private fun setupUI() {
        sessionAdapter.addLoadStateListener { loadStates ->
            when (loadStates.source.refresh) {
                is LoadState.NotLoading -> {
                    hideShimmer()
                }
                is LoadState.Loading -> {
                    if (sessionAdapter.itemCount == 0) {
                        binding.shimmerContainer.startShimmer()
                        binding.shimmerContainer.visibility = View.VISIBLE
                    } else {
                        hideShimmer()
                    }
                }
                is LoadState.Error -> {
                    hideShimmer()
                }
            }
        }
        binding.rvSessions.apply {
            adapter = sessionAdapter.withLoadStateFooter(
               CustomLoadStateAdapter {
                    sessionAdapter.retry()
                }
            )
        }
    }

    private fun hideShimmer() {
        binding.shimmerContainer.stopShimmer()
        binding.shimmerContainer.visibility = View.GONE
    }

    private fun setupObservers() {
        viewModel.apply {
            toast.observe(this@MainActivity) { e ->
                e.getContentIfNotHandled()?.let {
                    if (it is Int) {
                        binding.parentLayout.showSnackBar(getString(it))
                    } else {
                        binding.parentLayout.showSnackBar(it as String)
                    }
                }
            }

            sessionsLiveData.observe(this@MainActivity) { e ->
                lifecycleScope.launch {
                    sessionAdapter.submitData(e)
                    /*binding.parentLayout.showSnackBar(
                        it.joinToString(
                            separator = ",",
                            limit = 1,
                            truncated = "..."
                        )
                    )*/
                }
            }
        }
    }
}