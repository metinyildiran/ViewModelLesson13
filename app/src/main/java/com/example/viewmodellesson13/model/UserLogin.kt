package com.example.viewmodellesson13.model

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    class Error(val errorMessage: String) : LoginState()
}

data class UserLogin(val email: String, val password: String)
