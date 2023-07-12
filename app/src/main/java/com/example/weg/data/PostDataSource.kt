package com.example.weg.data

import android.util.Log
import com.example.weg.ui.ground.PostRecyclerItem
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class PostDataSource {
    fun getAllPost(groupName: String?,callback: (Result<ArrayList<PostRecyclerItem>>) -> Unit) {
        if(groupName.equals("")){
            callback(Result.Error(IOException()));
        }else{
            val url = "http://172.10.5.148:443/share/getGroupPost/$groupName";

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
                    // JSON 배열을 반복하면서 객체 추출
                    var memList: ArrayList<PostRecyclerItem> = ArrayList<PostRecyclerItem>();
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)

                        // JSON 객체에서 필요한 값 가져오기
                        val postWriter = jsonObject.getString("post_writer");
                        val postTitle = jsonObject.getString("post_title");
                        val postDetail = jsonObject.getString("post_detail");
                        val likeUsers = jsonObject.getJSONArray("like_users");
                        val likeUserList: ArrayList<String> =  ArrayList<String>();
                        for (j in 0 until likeUsers.length()) {
                            likeUserList.add(likeUsers.getString(j));
                        }
                        memList.add(PostRecyclerItem(postWriter, postTitle, postDetail, likeUserList))
                    }
                    callback(Result.Success(memList));
                }
            })
        }
    }

    fun getUserNickName(currentUserId:String?,currentGroup:String?,callback: (Result<String>) -> Unit){
        val url = "http://172.10.5.148:443/signup/getUserMyGroupProfiles/$currentUserId/$currentGroup";
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
    fun addNewPost(postWriter:String?,currentGroup:String?, postTitle: String?,postContent: String?,callback: (Result<PostRecyclerItem>) -> Unit) {
        if(postWriter != null && postTitle != null && postContent != null){
            val url = "http://172.10.5.148:443/share";

            val client = OkHttpClient();

            val requestBody = FormBody.Builder()
                .add("post_title", postTitle!!)
                .add("post_group", currentGroup!!)
                .add("post_detail", postContent)
                .add("post_writer", postWriter)
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
                    callback(Result.Success(PostRecyclerItem(postWriter!!, postTitle!!, postContent!!, ArrayList<String>())));
                }
            })

        }
    }
}