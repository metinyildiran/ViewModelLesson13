package com.example.viewmodellesson13.data.state

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    class Error(val errorMessage: String) : LoginState()
}