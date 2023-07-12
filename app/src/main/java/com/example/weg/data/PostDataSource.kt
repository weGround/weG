package com.example.weg.data

import com.example.weg.ui.ground.PostRecyclerItem
import okhttp3.Call
import okhttp3.Callback
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

    fun addNewPost(postWriter:String?, postTitle: String?,postContent: String?,callback: (Result<PostRecyclerItem>) -> Unit) {
        if(postWriter != null && postTitle != null && postContent != null){
            callback(Result.Success(PostRecyclerItem(postWriter!!, postTitle!!, postContent!!, ArrayList<String>())));
        }
    }
}