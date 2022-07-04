package com.sdsol.paginationsample.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sdsol.paginationsample.databinding.ActivityMainBinding
import com.sdsol.paginationsample.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

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
        viewModel.getSessions(1)
    }

    private fun setupUI() {
        binding.rvSessions.apply {
            adapter = sessionAdapter
        }
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

            isLoading.observe(this@MainActivity) {
                if (it) {
                    binding.shimmerContainer.startShimmer()
                    binding.shimmerContainer.visibility = View.VISIBLE
                } else {
                    binding.shimmerContainer.stopShimmer()
                    binding.shimmerContainer.visibility = View.GONE
                }
            }

            sessionsLiveData.observe(this@MainActivity) { e ->
                e.getContentIfNotHandled()?.let {
                    sessionAdapter.setSessionList(it)
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