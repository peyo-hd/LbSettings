package com.peyo.lbsettings;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

public class MenuItem {
    @SerializedName("icon") private String mIcon = "";
    @SerializedName("title") private String mTitle = "";

    public int getIconRes(Context context) {
        return context.getResources().getIdentifier(mIcon, "drawable",
                context.getPackageName());
    }
    public int getTitleRes(Context context) {
        return context.getResources().getIdentifier(mTitle, "string",
                context.getPackageName());
    }

}
