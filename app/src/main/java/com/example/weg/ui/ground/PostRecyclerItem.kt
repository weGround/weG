package com.example.weg.ui.ground


class PostRecyclerItem(var postWriter: String, var postTitle: String, var postDetail: String, var likeUsers: ArrayList<String>) {
    private var boolean = false;
    fun setPostWriterStr(postWriterStr: String) {
        this.postWriter = postWriterStr;
    }
    fun setPostTitleStr(postTitleStr: String) {
        this.postTitle = postTitleStr;
    }
    fun setPostDetailStr(postDetailStr: String) {
        this.postDetail = postDetailStr;
    }
    fun setLikeUsersList(likeUsersList: ArrayList<String>) {
        this.likeUsers = likeUsersList;
    }
    fun addLikeUsers(likeUser: String) {
        this.likeUsers.add(likeUser);
    }

    fun deleteLikeUsers(likeUser: String) {
        this.likeUsers.remove(likeUser);
    }

    fun getPostWriterStr(): String {
        return postWriter;
    }
    fun getPostTitleStr(): String {
        return postTitle;
    }
    fun getPostDetailStr(): String {
        return postDetail;
    }
    fun getLikeUsersList(): ArrayList<String> {
        return likeUsers;
    }
    fun isLiked(user:String): Boolean{
        return likeUsers.contains(user);
    }

}