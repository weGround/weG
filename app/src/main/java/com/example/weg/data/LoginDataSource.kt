package com.example.weg.data

import android.util.Log
import com.example.weg.data.model.LoggedInUser
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String, callback: (Result<LoggedInUser>) -> Unit) {
        try {
            // TODO: handle loggedInUser authentication
            val url = "http://172.10.5.148:443/blog"
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(url)
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                    Log.d("TAG", e.message.toString())
                    Log.d("TAG", "fail fail fail fail fail fail fail fail")
                    callback(Result.Error(IOException("Error logging in", e)))
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseBody: String = response.body?.string() ?: ""
                    Log.d("TAG", "login: $responseBody")
                    val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
                    callback(Result.Success(fakeUser))
                }
            })
        } catch (e: Throwable) {
            e.printStackTrace()
            Log.d("TAG", e.message.toString())
            Log.d("TAG", "fail fail fail fail fail fail fail fail")
            callback(Result.Error(IOException("Error logging in", e)))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}
