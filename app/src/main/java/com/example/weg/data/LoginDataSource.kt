package com.example.weg.data

import android.util.Log
import com.example.weg.ProfData
import com.example.weg.data.model.LoggedInUser
import com.example.weg.ui.login.KakaoResult
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    fun signUp(username: String, password: String, callback: (Result<LoggedInUser>) -> Unit) {

        // TODO: handle loggedInUser authentication
        val url = "http://172.10.5.148:443/signup"
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
                val jsonObject = JSONObject(responseBody)
                val msg = jsonObject.getString("message");
                if(msg.equals("already exist")){
                    Log.d("signup failed", "signup: $msg")
                    callback(Result.Error(IOException(msg)))
                }else if(msg.equals("signup success")){
                    Log.d("signup Success", "signup: $msg")
                    val signUpUser = LoggedInUser(username, password);
                    callback(Result.Success(signUpUser))
                }
            }
        })
    }
    fun login(username: String, password: String, callback: (Result<LoggedInUser>) -> Unit) {

        // handle loggedInUser authentication
        val url = "http://172.10.5.148:443/signup/login"
        val client = OkHttpClient()
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
                Log.d("TAG", "Fail to login")
                Log.d("TAG", e.message.toString())
                callback(Result.Error(IOException("Error logging in", e)))
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody: String = response.body?.string() ?: ""
                val jsonObject = JSONObject(responseBody)
                val msg = jsonObject.getString("message");
                if(msg.equals("login failed")){
                    Log.d("Login failed", "login: $msg")
                    callback(Result.Error(IOException(msg)))
                }else if(msg.equals("login success")){
                    Log.d("Login Success", "login: $msg")
                    val loginUser = LoggedInUser(username, password);
                    callback(Result.Success(loginUser))
                }
            }
        })

    }

    fun getKakaoAlreadyExist(userId: String, callback: (Result<KakaoResult>) -> Unit) {
        val url = "http://172.10.5.148:443/signup/getUser/$userId";

        val client = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(Result.Error(IOException(e.message.toString(), e)))
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody: String = response.body?.string() ?: ""

                try{
                    val jsonObject = JSONObject(responseBody);
                    val temp = jsonObject.getString("message");
                    // 존재하지 않으면 message가 존재
                    Log.d("KAKAO", "message가 있다있다!! result가 false야")
                    callback(Result.Success(KakaoResult(false, userId, "kakaopwd$userId")));
                }catch (e:Exception){
                    Log.d("KAKAO", "message가 없다 없다!! result가 true야")
                    callback(Result.Success(KakaoResult(true, userId, "kakaopwd$userId")));
                }
            }
        })
    }

    fun logout() {
        // TODO: revoke authentication
    }
}
