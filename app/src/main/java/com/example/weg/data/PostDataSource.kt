package com.example.weg.data

import com.example.weg.ui.ground.PostRecyclerItem
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException

class PostDataSource {
    fun getAllPost(groupName: String?,callback: (Result<ArrayList<PostRecyclerItem>>) -> Unit) {
        if(groupName == null){
            callback(Result.Error(IOException()));
        }else {
            var postList: ArrayList<PostRecyclerItem> = ArrayList<PostRecyclerItem>();
            postList.add(PostRecyclerItem("Jihwan", "오늘의 한줄", "과연 내가 이걸 완성할 수 있을까", arrayListOf("사람1","사람2", "사람3", "사람4")));
            postList.add(PostRecyclerItem("Yejin", "오늘의 한줄", "밥 먹었으니까 3시간 뒤에 잘거야", arrayListOf("사람1","사람2", "사람3", "사람4")));
            postList.add(PostRecyclerItem("SeungJong", "오늘의 한줄", "오늘은 질문하는 학생들이 적구나", arrayListOf("사람1","사람2", "사람3", "사람4")));
            postList.add(PostRecyclerItem("Jihwan", "오늘의 한줄", "과연 내가 이걸 완성할 수 있을까", arrayListOf("사람1","사람2", "사람3", "사람4")));
            postList.add(PostRecyclerItem("Yejin", "오늘의 한줄", "밥 먹었으니까 3시간 뒤에 잘거야", arrayListOf("사람1","사람2", "사람3", "사람4")));
            postList.add(PostRecyclerItem("SeungJong", "오늘의 한줄", "오늘은 질문하는 학생들이 적구나", arrayListOf("사람1","사람2", "사람3", "사람4")));
            postList.add(PostRecyclerItem("Jihwan", "오늘의 한줄", "과연 내가 이걸 완성할 수 있을까", arrayListOf("사람1","사람2", "사람3", "사람4")));
            postList.add(PostRecyclerItem("Yejin", "오늘의 한줄", "밥 먹었으니까 3시간 뒤에 잘거야", arrayListOf("사람1","사람2", "사람3", "사람4")));
            postList.add(PostRecyclerItem("SeungJong", "오늘의 한줄", "오늘은 질문하는 학생들이 적구나", arrayListOf("사람1","사람2", "사람3", "사람4")));
            callback(Result.Success(postList));
        }

//        // TODO: handle loggedInUser authentication
//        val url = "http://172.10.5.148:443/group/getMems/$groupName";

//        val client = OkHttpClient();
//
//        val request = Request.Builder()
//            .url(url)
//            .build()

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