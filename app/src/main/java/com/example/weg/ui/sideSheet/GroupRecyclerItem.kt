package com.example.weg.ui.sideSheet

import android.graphics.drawable.Drawable

class GroupRecyclerItem(var groupNameStr: String, var groupImgDrawable: Drawable?) {
    fun setGroupImage(groupImg: Drawable) {
        this.groupImgDrawable = groupImg;
    }
    fun setGroupName(groupName: String) {
        this.groupNameStr = groupName;
    }

    fun getGroupImg(): Drawable? {
        return this.groupImgDrawable;
    }
    fun getGroupName(): String {
        return this.groupNameStr;
    }

}