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

    fun addGroup(userId:String, newGroupName: String, callback: (Result<String>) -> Unit)  {
        // TODO: handle loggedInUser authentication
        val groupUrl = "http://172.10.5.148:443/group/updateMems/$newGroupName";
        val groupJoinUrl = "http://172.10.5.148:443/signup/joinGroup/$userId";
        val client2 = OkHttpClient();

        val requestBody2 = FormBody.Builder()
            .add("newmember", userId)
            .build()

        val request2 = Request.Builder()
            .url(groupUrl)
            .put(requestBody2)
            .build()

        client2.newCall(request2).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(Result.Error(IOException(e.message.toString(), e)))
            }
            override fun onResponse(call: Call, response: Response) {
                callback(Result.Success(newGroupName));
            }
        })

        val client3 = OkHttpClient();

        val requestBody3 = FormBody.Builder()
            .add("groupname", newGroupName)
            .build()

        val request3 = Request.Builder()
            .url(groupJoinUrl)
            .post(requestBody3)
            .build()

        client3.newCall(request3).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(Result.Error(IOException(e.message.toString(), e)))
            }
            override fun onResponse(call: Call, response: Response) {
                callback(Result.Success(newGroupName));
            }
        })




    }

    fun makeGroup(newGroupName: String, newUserId:String, callback: (Result<String>) -> Unit)  {
        // TODO: handle loggedInUser authentication
        val createGroupUrl = "http://172.10.5.148:443/group";

        val client2 = OkHttpClient();

        val requestBody2 = FormBody.Builder()
            .add("groupname", newGroupName)
            .add("groupinfo", "Our Group is $newGroupName")
            .build()

        val request2 = Request.Builder()
            .url(createGroupUrl)
            .post(requestBody2)
            .build()

        client2.newCall(request2).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(Result.Error(IOException(e.message.toString(), e)))
            }
            override fun onResponse(call: Call, response: Response) {
                callback(Result.Success(newGroupName));
            }
        })

        val groupJoinUrl = "http://172.10.5.148:443/group/updateMems/$newGroupName";
        val client = OkHttpClient();

        val requestBody = FormBody.Builder()
            .add("newmember", newUserId)
            .build()

        val request = Request.Builder()
            .url(groupJoinUrl)
            .put(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(Result.Error(IOException(e.message.toString(), e)))
            }
            override fun onResponse(call: Call, response: Response) {
                callback(Result.Success(newGroupName));
            }
        })

        val groupJoinUserUrl = "http://172.10.5.148:443/signup/joinGroup/$newUserId";
        val client3 = OkHttpClient();

        val requestBody3 = FormBody.Builder()
            .add("groupname", newGroupName)
            .build()

        val request3 = Request.Builder()
            .url(groupJoinUserUrl)
            .post(requestBody3)
            .build()

        client3.newCall(request3).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(Result.Error(IOException(e.message.toString(), e)))
            }
            override fun onResponse(call: Call, response: Response) {
                callback(Result.Success(newGroupName));
            }
        })
    }
}
