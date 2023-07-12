package com.example.weg.data

import android.util.Log
import com.example.weg.ProfData
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

        val url = "http://172.10.5.148:443/group/getGroup/$groupName";
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
                    val groupInfo = jsonObject.getString("groupinfo");
                    callback(Result.Success(groupInfo));
                }catch (e:Exception){
                    callback(Result.Success("No Info"));
                }

            }
        })
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

    fun getUserNickName(currentUserId : String?, currentGroupName : String?, callback: (Result<String>) -> Unit) {
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
                    val nickname = jsonObject.getString("mygroup_nickname");
                    callback(Result.Success(nickname));
                }catch (e:Exception){
                    callback(Result.Success("No Detail"));
                }
            }
        })
    }

    fun getUserDetail(userNickName :String, currentUserId : String?, currentGroupName : String?, callback: (Result<ProfData>) -> Unit) {
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
                    callback(Result.Success(ProfData(userNickName,detail, null)));
                }catch (e:Exception){
                    callback(Result.Success(ProfData("no data","no data", null)));
                }
            }
        })
    }

}