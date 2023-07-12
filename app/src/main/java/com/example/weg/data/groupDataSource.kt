package com.example.weg.data

import android.util.Log
import android.widget.Toast
import com.example.weg.data.model.LoggedInUser
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class groupDataSource {
    fun getGroupList(currentUserId:String?, callback: (Result<ArrayList<String>>) -> Unit)  {
        val url = "http://172.10.5.148:443/signup/getUserMyGroupLists/$currentUserId";
        Log.d("get Nickname", "This is url : $url")
        val client = OkHttpClient();

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
                Log.d("get Nickname", "This is responseBody : $responseBody")
                try{
                    val jsonArray = JSONArray(responseBody);
                    var groupList : ArrayList<String> = ArrayList<String>();
                    for (i in 0 until jsonArray.length()) {
                        groupList.add(jsonArray.get(i).toString())
                    }
                    Log.d("GroupDataSource", "THis is the size of real group list : " + groupList.size)
                    callback(Result.Success(groupList));
                }catch (e:Exception){
                    callback(Result.Success(ArrayList<String>()));
                }

            }
        })
    }

    fun addGroup(newGroupName: String, callback: (Result<String>) -> Unit)  {
        // TODO: handle loggedInUser authentication
        val url = "http://172.10.5.148:443/signup"
        val client = OkHttpClient();

//        val requestBody = FormBody.Builder()
//            .add("groupname", username)
//            .build()

//        val request = Request.Builder()
//            .url(url)
//            .post(requestBody)
//            .build()
//
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//                callback(Result.Error(IOException(e.message.toString(), e)))
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                val responseBody: String = response.body?.string() ?: ""
//                val jsonObject = JSONObject(responseBody)
//                val msg = jsonObject.getString("message");
//                if(msg.equals("already exist")){
//                    Log.d("signup failed", "signup: $msg")
//                    callback(Result.Error(IOException(msg)))
//                }else if(msg.equals("signup success")){
//                    Log.d("signup Success", "signup: $msg")
//                    val signUpUser = LoggedInUser(username, password);
//                    callback(Result.Success(signUpUser))
//                }
//            }
//        })
    }

    fun makeGroup(newGroupName: String, callback: (Result<String>) -> Unit)  {
        // TODO: handle loggedInUser authentication
        val url = "http://172.10.5.148:443/signup"
        val client = OkHttpClient();

//        val requestBody = FormBody.Builder()
//            .add("groupname", username)
//            .build()

//        val request = Request.Builder()
//            .url(url)
//            .post(requestBody)
//            .build()
//
//        client.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                e.printStackTrace()
//                callback(Result.Error(IOException(e.message.toString(), e)))
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                val responseBody: String = response.body?.string() ?: ""
//                val jsonObject = JSONObject(responseBody)
//                val msg = jsonObject.getString("message");
//                if(msg.equals("already exist")){
//                    Log.d("signup failed", "signup: $msg")
//                    callback(Result.Error(IOException(msg)))
//                }else if(msg.equals("signup success")){
//                    Log.d("signup Success", "signup: $msg")
//                    val signUpUser = LoggedInUser(username, password);
//                    callback(Result.Success(signUpUser))
//                }
//            }
//        })
    }
}