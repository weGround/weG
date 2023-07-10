package com.example.weg

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.weg.ui.home.HomeMemberProfileFragment

class profileData(var userName : String, var userIntro : String, var userImage : Drawable ) {

    fun setName(newName : String){
        this.userName = newName;
    }
    fun setIntro(newIntro : String){
        this.userIntro = newIntro;
    }
    fun setImage(newImage : Drawable){
        this.userImage = newImage;
    }
    fun getName() :String {
        return this.userName;
    }
    fun getIntro() :String {
        return this.userIntro;
    }

    fun getImage() :Drawable {
        return this.userImage;
    }

}