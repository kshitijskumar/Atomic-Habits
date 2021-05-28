package com.example.atomichabits.ui.uploadtask

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.atomichabits.databinding.FragmentUploadTaskBinding
import com.example.atomichabits.utils.Resource
import com.example.atomichabits.utils.UtilFunctions.showToast
import com.example.atomichabits.viewmodels.UserViewModel
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class UploadTaskFragment : Fragment() {

    private var _binding: FragmentUploadTaskBinding? = null
    private val binding: FragmentUploadTaskBinding get() = _binding!!

    private val viewModel by lazy {
        UserViewModel.getUserViewModel(this, false)
    }

    lateinit var imagePath: String
    private var uri: Uri? = null
    private val takePhoto = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if(it) {
            binding.ivImage.setImageURI(uri)
        }else {
            Log.d("ClickingPhoto", "Clicking photo failed")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUploadTaskBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeValues()

        binding.btnClick.setOnClickListener {
            dispatchTakePicture()
        }
        binding.btnUpload.setOnClickListener {
            val caption = binding.etExperience.text.toString()
            viewModel.uploadActivity(uri, caption)
        }
    }

    private fun createImageFile() : File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
            .format(Date())
        val storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            imagePath = absolutePath
        }
    }

    private fun dispatchTakePicture() {
        val photoFile: File? = try {
            createImageFile()
        }catch (e: Exception) {
            e.printStackTrace()
            null
        }

        photoFile?.also {
            val photoUri: Uri = FileProvider.getUriForFile(
                requireContext(),
                "com.example.atomichabits.ui.uploadtask",
                it
            )
            uri = photoUri
            takePhoto.launch(photoUri)
        }
    }

    private fun observeValues() {
        viewModel.upload.observe(viewLifecycleOwner) {
            when(it) {
                is Resource.Success -> {
                    requireContext().showToast("Upload successfully.")
                    findNavController().navigateUp()
                }
                is Resource.Error -> requireContext().showToast(it.errorMsg)
                is Resource.Loading -> requireContext().showToast("Please wait while we upload.")
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}