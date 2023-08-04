package com.example.viewmodellesson13.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viewmodellesson13.model.LoginState
import com.example.viewmodellesson13.model.UserLogin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(userLogin: UserLogin) {
        viewModelScope.launch {
            _loginState.emit(LoginState.Loading)

            delay(2000)

            if (userLogin.email.isEmpty() || userLogin.password.isEmpty()) {
                _loginState.emit(LoginState.Error("Lütfen email veya şifre giriniz"))
            } else {
                _loginState.emit(LoginState.Success)
            }
        }
    }
}