package com.example.weg.data

import com.example.weg.data.model.LoggedInUser
import com.example.weg.ui.login.LoginViewModel

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource) {

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    fun login(loginViewModel: LoginViewModel, username: String, password: String) {

        // handle login
        dataSource.login(username, password) {
            if (it is Result.Success) {
                setLoggedInUser(it.data)
                loginViewModel.getLoginCheckResult(it);
            }
        }
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}