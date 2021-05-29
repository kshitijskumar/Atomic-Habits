package com.example.atomichabits.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.atomichabits.databinding.FragmentFeedBinding
import com.example.atomichabits.utils.Resource
import com.example.atomichabits.utils.UtilFunctions.showToast
import com.example.atomichabits.viewmodels.UserViewModel

class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding: FragmentFeedBinding get() = _binding!!

    private lateinit var feedAdapter: FeedAdapter

    private val viewModel by lazy {
        UserViewModel.getUserViewModel(this, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeValues()

        binding.swipeRefresh.setOnRefreshListener {
            //fetch post function from viewmodel
        }
    }

    private fun observeValues() {
        viewModel.posts.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    stopLoading()
                    feedAdapter.submitList(it.data)
                }
                is Resource.Error -> {
                    stopLoading()
                    requireContext().showToast(it.errorMsg)
                }
                is Resource.Loading -> {
                    startLoading()
                }
            }
        }
    }

    private fun stopLoading() {
        binding.swipeRefresh.isRefreshing = false
    }

    private fun startLoading() {
        binding.swipeRefresh.isRefreshing = true
    }

    private fun setupRecyclerView() {
        feedAdapter = FeedAdapter()
        binding.rvFeed.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = feedAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}