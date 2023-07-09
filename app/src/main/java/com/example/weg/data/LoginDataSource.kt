package com.example.weg.data

import android.util.Log
import com.example.weg.data.model.LoggedInUser
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    fun signUp(username: String, password: String, callback: (Result<LoggedInUser>) -> Unit) {

        // TODO: handle loggedInUser authentication
        val url = "http://172.10.5.148:443/blog"
        val client = OkHttpClient();

            val requestBody = FormBody.Builder()
                .add("userid", username)
                .add("pw", password)
                .build()

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(Result.Error(IOException(e.message.toString(), e)))
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody: String = response.body?.string() ?: ""

                Log.d("TAG", "login: $responseBody")
                val signUpUser = LoggedInUser(username, password);
                callback(Result.Success(signUpUser))
            }
        })
    }
    fun login(username: String, password: String, callback: (Result<LoggedInUser>) -> Unit) {

        // handle loggedInUser authentication
        val url = "http://172.10.5.148:443/blog"
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                Log.d("TAG", "Fail to login")
                Log.d("TAG", e.message.toString())
                callback(Result.Error(IOException("Error logging in", e)))
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody: String = response.body?.string() ?: "";
                Log.d("TAG", "login: $responseBody");
                val loginUser = LoggedInUser(username, password);
                callback(Result.Success(loginUser));
            }
        })

    }

    fun logout() {
        // TODO: revoke authentication
    }
}
