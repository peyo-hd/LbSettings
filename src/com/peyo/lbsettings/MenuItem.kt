package com.peyo.lbsettings

import android.content.Context

import com.google.gson.annotations.SerializedName

class MenuItem {
    @SerializedName("icon")
    private val mIcon = ""
    @SerializedName("title")
    private val mTitle = ""

    fun getIconRes(context: Context): Int {
        return context.resources.getIdentifier(mIcon, "drawable", context.packageName)
    }

    fun getTitleRes(context: Context): Int {
        return context.resources.getIdentifier(mTitle, "string", context.packageName)
    }

}
