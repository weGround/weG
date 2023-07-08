package com.example.weg.ui.sideSheet

class GroupRecyclerItem(var groupNameStr: String) {
    fun setGroupName(groupName: String) {
        this.groupNameStr = groupName;
    }
    fun getGroupName(): String {
        return this.groupNameStr;
    }
}