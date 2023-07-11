package com.example.weg.data

class ProfileDataSource {
    fun modifyUserNickName(newUserName: String, currentUserId : String?, currentGroupName : String?, callback: (Result<String>) -> Unit) {

//        // TODO: handle loggedInUser authentication
        callback(Result.Success("My name is $newUserName"));
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

    fun modifyUserDetail(newUserDetail: String, currentUserId : String?, currentGroupName : String?, callback: (Result<String>) -> Unit) {

//        // TODO: handle loggedInUser authentication
        callback(Result.Success("My new Intro is $newUserDetail"));
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