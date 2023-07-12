package com.example.weg.data

import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class ProfileDataSource {
    fun getNickname(currentUserId : String?, currentGroupName : String?, callback: (Result<String>) -> Unit) {

//        // TODO: handle loggedInUser authentication
//        callback(Result.Success("My new Intro is $currentUserId"));
        val url = "http://172.10.5.148:443/signup/getUserMyGroupProfiles/$currentUserId/$currentGroupName";
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
                    val jsonObject = JSONObject(responseBody);
                    val nickname = jsonObject.getString("mygroup_nickname");
                    callback(Result.Success(nickname));
                }catch (e:Exception){
                    callback(Result.Success("No Name"));
                }

            }
        })
    }

    fun getUserDetail(currentUserId : String?, currentGroupName : String?, callback: (Result<String>) -> Unit) {

//        // TODO: handle loggedInUser authentication
        val url = "http://172.10.5.148:443/signup/getUserMyGroupProfiles/$currentUserId/$currentGroupName";

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
                try{
                    val jsonObject = JSONObject(responseBody);
                    val detail = jsonObject.getString("mygroup_detail");
                    callback(Result.Success(detail));
                }catch (e:Exception){
                    callback(Result.Success("No Detail"));
                }
            }
        })
    }
    fun modifyUserNickName(newUserName: String, currentGroupIntro :String, currentUserId : String?, currentGroupName : String?, callback: (Result<String>) -> Unit) {

//        // TODO: handle loggedInUser authentication
        val url = "http://172.10.5.148:443/signup/editUserMyGroupProfiles/$currentUserId/$currentGroupName";

        val client = OkHttpClient();

        val requestBody = FormBody.Builder()
            .add("userid", currentUserId!!)
            .add("groupname", currentGroupName!!)
            .add("mygroup_nickname", newUserName)
            .add("mygroup_detail", currentGroupIntro)
            .build()

        val request = Request.Builder()
            .url(url)
            .put(requestBody)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(Result.Error(IOException(e.message.toString(), e)))
            }

            override fun onResponse(call: Call, response: Response) {
                callback(Result.Success(newUserName));
            }
        })
    }

    fun modifyUserDetail(newUserDetail: String,currentUserNickName: String, currentUserId : String?, currentGroupName : String?, callback: (Result<String>) -> Unit) {

//        // TODO: handle loggedInUser authentication
        val url = "http://172.10.5.148:443/signup/editUserMyGroupProfiles/$currentUserId/$currentGroupName";

        val client = OkHttpClient();

        val requestBody = FormBody.Builder()
            .add("userid", currentUserId!!)
            .add("groupname", currentGroupName!!)
            .add("mygroup_nickname", currentUserNickName)
            .add("mygroup_detail", newUserDetail)
            .build()

        val request = Request.Builder()
            .url(url)
            .put(requestBody)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(Result.Error(IOException(e.message.toString(), e)))
            }

            override fun onResponse(call: Call, response: Response) {
                callback(Result.Success(newUserDetail));
            }
        })
    }

    fun exitFromGroup(currentUserId : String?, currentGroupName : String?, callback: (Result<String>) -> Unit) {
        val url = "http://172.10.5.148:443/signup/exitGroup/$currentUserId/$currentGroupName";

        val client = OkHttpClient();

        val request = Request.Builder()
            .url(url)
            .delete()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(Result.Error(IOException(e.message.toString(), e)))
            }

            override fun onResponse(call: Call, response: Response) {
                callback(Result.Success(currentUserId!!));
            }
        })

        val url2 = "http://172.10.5.148:443/group/deleteMems/$currentGroupName";

        val client2 = OkHttpClient();
        val requestBody = FormBody.Builder()
            .add("deletemember", currentUserId!!)
            .build()

        val request2 = Request.Builder()
            .url(url2)
            .delete(requestBody)
            .build()

        client2.newCall(request2).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(Result.Error(IOException(e.message.toString(), e)))
            }

            override fun onResponse(call: Call, response: Response) {
                callback(Result.Success(currentUserId!!));
            }
        })

    }

}