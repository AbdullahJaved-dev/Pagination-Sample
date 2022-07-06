package com.sdsol.paginationsample.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.sdsol.paginationsample.R
import com.sdsol.paginationsample.databinding.ActivityMainBinding
import com.sdsol.paginationsample.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

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
                    when (val exception = (loadStates.source.refresh as LoadState.Error).error) {
                        is SocketTimeoutException -> {
                            binding.parentLayout.showSnackBar(getString(R.string.slower_internet_connection))
                        }
                        is TimeoutException -> {
                            binding.parentLayout.showSnackBar(getString(R.string.slower_internet_connection))
                        }
                        is HttpException -> {
                            binding.parentLayout.showSnackBar(getString(R.string.unknown_error_occoured))
                        }
                        is UnknownHostException -> {
                            binding.parentLayout.showSnackBar(getString(R.string.slower_internet_connection))
                        }
                        else -> {
                            binding.parentLayout.showSnackBar(
                                exception.message ?: exception.toString()
                            )
                        }
                    }
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

            lifecycleScope.launch {
                sessionsFlow.collectLatest {
                    sessionAdapter.submitData(it)
                }
            }

        }
    }
}