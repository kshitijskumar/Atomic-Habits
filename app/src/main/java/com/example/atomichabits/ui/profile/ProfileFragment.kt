package com.example.atomichabits.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.atomichabits.databinding.FragmentProfileBinding
import com.example.atomichabits.utils.Resource
import com.example.atomichabits.utils.UtilFunctions.showToast
import com.example.atomichabits.viewmodels.UserViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding get() = _binding!!

    private val viewModel by lazy {
        UserViewModel.getUserViewModel(this, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fetchUserDetails()
        setupViews()
    }

    private fun setupViews() {
        viewModel.userDetails.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    binding.apply {
                        progressBar3.visibility = View.GONE
                        tvName.text = it.data.name
                        tvMail.text = it.data.email
                        tvStreakCount.text = it.data.streak.toString()
                    }
                    Glide.with(requireContext())
                        .load("https:${it.data.avatar}")
                        .circleCrop()
                        .into(binding.ivProfileImg)
                }
                is Resource.Error -> {
                    binding.progressBar3.visibility = View.GONE
                    requireContext().showToast(it.errorMsg)
                }
                is Resource.Loading -> {
                    binding.progressBar3.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}