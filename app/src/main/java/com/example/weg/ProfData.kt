package com.example.weg

import android.graphics.drawable.Drawable

class ProfData (var userName : String, var userIntro : String, var userImage : Drawable? ){

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

    fun getImage() : Drawable? {
        return this.userImage;
    }
}