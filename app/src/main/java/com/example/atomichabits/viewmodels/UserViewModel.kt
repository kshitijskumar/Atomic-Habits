package com.example.atomichabits.viewmodels

import androidx.lifecycle.*
import com.example.atomichabits.data.repository.UserRepository
import com.example.atomichabits.data.response.ActivityResponse
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