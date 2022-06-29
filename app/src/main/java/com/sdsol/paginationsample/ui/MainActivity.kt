package com.sdsol.paginationsample.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sdsol.paginationsample.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        viewModel.getSessions(1)
    }

    private fun setupObservers() {
        viewModel.apply {
            toast.observe(this@MainActivity) { e ->
                e.getContentIfNotHandled()?.let {
                    if (it is Int) {
                        Toast.makeText(this@MainActivity, getString(it), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MainActivity, it as String, Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(this@MainActivity, it.joinToString { "," }, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}