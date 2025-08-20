package com.example.shift87375.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shift87375.data.UsersRepository
import com.example.shift87375.db.AppDatabase
import com.example.shift87375.model.User
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class UsersViewModel(application: Application) : AndroidViewModel(application) {

    sealed interface UiState {
        data object Loading : UiState
        data class Success(val users: List<User>) : UiState
        data class Error(val error: Throwable) : UiState
    }

    private val repo by lazy {
        val db = AppDatabase.getInstance(getApplication())
        UsersRepository(db.userDao())
    }

    private val _state = MutableLiveData<UiState>()
    val state: LiveData<UiState> = _state
    fun loadFromCache() {
        viewModelScope.launch {
            val cached = repo.getCachedUsers()
            if (cached.isNotEmpty()) {
                _state.value = UiState.Success(cached)
            } else {
                _state.value = UiState.Loading
                repo.refreshUsers(26).fold(
                    onSuccess = { fresh -> _state.value = UiState.Success(fresh) },
                    onFailure = { err -> _state.value = UiState.Error(err) }
                )
            }
        }
    }
    fun refresh(count: Int = 26) {
        viewModelScope.launch {
            _state.value = UiState.Loading
            repo.refreshUsers(count).fold(
                onSuccess = { fresh -> _state.value = UiState.Success(fresh) },
                onFailure = { err -> _state.value = UiState.Error(err) }
            )
        }
    }
}