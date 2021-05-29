package com.example.atomichabits.viewmodels

import android.net.Uri
import androidx.lifecycle.*
import com.example.atomichabits.data.repository.UserRepository
import com.example.atomichabits.data.response.ActivityResponse
import com.example.atomichabits.data.response.PostResponse
import com.example.atomichabits.data.response.UserResponse
import com.example.atomichabits.utils.Injector
import com.example.atomichabits.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class UserViewModel(
    private val isTask: Boolean = false,
    private val repo: UserRepository
) : ViewModel() {


    private val _activity = MutableLiveData<Resource<ActivityResponse>>()
    val activity: LiveData<Resource<ActivityResponse>> = _activity

    private val _upload = MutableLiveData<Resource<Any>>()
    val upload: LiveData<Resource<Any>> = _upload

    private val _posts = MutableLiveData<Resource<List<PostResponse>>>()
    val posts: LiveData<Resource<List<PostResponse>>> = _posts

    private val _userDetails = MutableLiveData<Resource<UserResponse>>()
    val userDetails: LiveData<Resource<UserResponse>> = _userDetails

    init {
        if(isTask) {
            getRandomTask()
        }
    }

    private fun getRandomTask() = viewModelScope.launch {
        repo.getARandomActivity().collect {
            _activity.postValue(it)
        }
    }

    fun uploadActivity(uri: Uri?, caption: String) = viewModelScope.launch {
        when{
            uri == null -> _upload.postValue(Resource.Error("Please select an imaage"))
            caption.isEmpty() -> _upload.postValue(Resource.Error("Please share your experience"))
            else -> {
                repo.uploadActivity().collect {
                    _upload.postValue(it)
                }
            }
        }
    }

    fun fetchUserDetails() = viewModelScope.launch {
        repo.fetchUserDetails().collect {
            _userDetails.postValue(it)
        }
    }



    companion object {
        private class UserViewModelFactory(private val isTask: Boolean) : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo = Injector.getInjector().provideUserRepo()
                return UserViewModel(isTask, repo) as T
            }
        }

        fun getUserViewModel(owner: ViewModelStoreOwner, isTask: Boolean) : UserViewModel {
            return ViewModelProvider(owner, UserViewModelFactory(isTask))[UserViewModel::class.java]
        }
    }
}