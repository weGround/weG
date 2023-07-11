package com.example.weg.data

import android.util.Log
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

class HomeDataSource {

    fun getGroupInfo(groupName: String,callback: (Result<String>) -> Unit) {

//        // TODO: handle loggedInUser authentication
        val infoStr = "Our Group Name is $groupName";
        callback(Result.Success(infoStr));
//        val url = "http://172.10.5.148:443/group/getMems/$groupName";
//
//        val client = OkHttpClient();
//
//        val request = Request.Builder()
//            .url(url)
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
//                callback(Result.Success(responseBody));
//            }
//        })
    }
    fun getGroupMem(groupName: String,callback: (Result<ArrayList<String>>) -> Unit) {

//        // TODO: handle loggedInUser authentication
        val url = "http://172.10.5.148:443/group/getMems/$groupName";

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
                val jsonArray = JSONArray(responseBody);
                var memList : ArrayList<String> = ArrayList<String>();
                for (i in 0 until jsonArray.length()) {
                    memList.add(jsonArray.get(i).toString())
                }
                callback(Result.Success(memList));
            }
        })
    }

    fun getUserDetail(userName: String,callback: (Result<String>) -> Unit) {

//        // TODO: handle loggedInUser authentication
        callback(Result.Success("My name is $userName"));
//        val url = "http://172.10.5.148:443/signUp/detail/" + userName;
//
//        val client = OkHttpClient();
//
//        val request = Request.Builder()
//            .url(url)
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
//                val jsonArray = JSONArray(responseBody);
//                var memList : ArrayList<String> = ArrayList<String>();
//                for (i in 0 until jsonArray.length()) {
//                    memList.add(jsonArray.get(i).toString())
//                }
//                callback(Result.Success(memList));
//            }
//        })
    }

}