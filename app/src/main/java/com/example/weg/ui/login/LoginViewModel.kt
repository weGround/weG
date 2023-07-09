package com.example.weg.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.example.weg.data.LoginRepository
import com.example.weg.data.Result

import com.example.weg.R
import com.example.weg.data.model.LoggedInUser
import java.nio.file.Files.getOwner

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    private val _signUpResult = MutableLiveData<LoginResult>()
    val signUpResult: LiveData<LoginResult> = _signUpResult

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        loginRepository.login(this, username, password)
    }

    fun signUp(username: String, password: String) {
        // can be launched in a separate asynchronous job
        loginRepository.signUp(this, username, password)
    }

    fun getLoginCheckResult(result: Result<LoggedInUser>){
        if (result is Result.Success) {
            _loginResult.postValue(LoginResult(success = LoggedInUserView(userId = result.data.userId)));
        } else {
            // TODO : exception 종류에 따라 서로 다른 error 지정해줘야함.
            Log.d("login log", result.toString());
            _loginResult.postValue(LoginResult(error = result.toString()));
        }
    }

    fun getSignUpCheckResult(result: Result<LoggedInUser>){
        if (result is Result.Success) {
            _signUpResult.postValue(LoginResult(success = LoggedInUserView(userId = result.data.userId)));
        } else {
            _signUpResult.postValue(LoginResult(error = result.toString()));
        }
    }
    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}