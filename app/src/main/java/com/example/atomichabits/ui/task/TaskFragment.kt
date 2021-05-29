package com.example.atomichabits.ui.task

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.atomichabits.R
import com.example.atomichabits.databinding.FragmentTaskBinding
import com.example.atomichabits.utils.Resource
import com.example.atomichabits.utils.UtilFunctions.showToast
import com.example.atomichabits.viewmodels.UserViewModel

class TaskFragment : Fragment() {

    private var _binding: FragmentTaskBinding? = null
    private val binding: FragmentTaskBinding get() =  _binding!!

    private val viewModel by lazy {
        UserViewModel.getUserViewModel(this, true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        observeValues()
    }

    private fun setupViews() {
        binding.btnShare.setOnClickListener {
            findNavController().navigate(R.id.action_taskFragment_to_uploadTaskFragment)
        }
    }

    private fun observeValues() {
        viewModel.activity.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    stopLoading()
                    binding.tvTask.text = it.data.title
                    binding.tvDescription.text = it.data.desc
                }
                is Resource.Loading -> {
                    startLoading()
                }
                is Resource.Error -> {
                    stopLoading()
                    requireContext().showToast(it.errorMsg)
                }
            }
        }
    }

    private fun stopLoading() {
        binding.progressBar2.visibility = View.GONE
    }

    private fun startLoading() {
        binding.progressBar2.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}