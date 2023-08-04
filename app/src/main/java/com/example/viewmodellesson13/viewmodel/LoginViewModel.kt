package com.example.viewmodellesson13.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewmodellesson13.Database
import com.example.viewmodellesson13.data.state.LoginState
import com.example.viewmodellesson13.data.model.UserLogin
import com.example.viewmodellesson13.data.state.ReactionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    private val _reactionState: MutableStateFlow<ReactionState> = MutableStateFlow(ReactionState.Pleased)
    val reactionState: StateFlow<ReactionState> = _reactionState

    fun login(userLogin: UserLogin) {
        viewModelScope.launch {
            if (userLogin.email.isNotEmpty() && userLogin.password.isNotEmpty()) {
                _loginState.emit(LoginState.Loading)

                delay(2000)

                Database.users.firstOrNull { it.email == userLogin.email && it.password == userLogin.password }?.let {
                    _loginState.emit(LoginState.Success)
                    _reactionState.emit(ReactionState.Happy)

                    delay(1000)
                } ?: kotlin.run {
                    _loginState.emit(LoginState.Error("Email veya şifre yanlış"))
                    _reactionState.emit(ReactionState.Shocked)
                }
            } else {
                _loginState.emit(LoginState.Error("Lütfen email veya şifre giriniz"))
                _reactionState.emit(ReactionState.Angry)
            }
        }
    }
}