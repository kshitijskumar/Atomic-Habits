package com.example.atomichabits.viewmodels

import android.util.Patterns
import androidx.lifecycle.*
import com.example.atomichabits.data.models.LoginModel
import com.example.atomichabits.data.repository.AuthRepository
import com.example.atomichabits.data.response.LoginResponse
import com.example.atomichabits.utils.Injector
import com.example.atomichabits.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class AuthViewModel(
    private val repo: AuthRepository
) : ViewModel() {

    private val _loginState = MutableLiveData<Resource<LoginResponse>>()
    val loginState : LiveData<Resource<LoginResponse>> = _loginState

    fun loginExistingUser(
        email: String?,
        password: String?
    ) = viewModelScope.launch {
        _loginState.postValue(Resource.Loading)
        when {
            email.isNullOrEmpty() -> _loginState.postValue(Resource.Error("Please enter email."))
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _loginState.postValue(Resource.Error("Please enter a valid email."))
            }
            password.isNullOrEmpty() -> _loginState.postValue(Resource.Error("Please enter password."))
            else -> {
                repo.loginExistingUser(
                    LoginModel(email, password)
                ).collect {
                    _loginState.postValue(it)
                }
            }
        }
    }

    companion object {
        private class AuthViewModelFactory() : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                val repo = Injector.getInjector().provideAuthRepo()
                return AuthViewModel(repo) as T
            }
        }

        fun getAuthViewModel(owner: ViewModelStoreOwner) : AuthViewModel {
            return ViewModelProvider(owner, AuthViewModelFactory())[AuthViewModel::class.java]
        }
    }
}