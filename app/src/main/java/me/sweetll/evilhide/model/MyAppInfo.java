package me.sweetll.evilhide.model;


import android.content.pm.ApplicationInfo;

/**
 * Created by sweet on 3/6/16.
 */
public class MyAppInfo {
    public ApplicationInfo applicationInfo;
    public boolean hidden;
    public boolean star;
    public String password;

    public MyAppInfo(ApplicationInfo applicationInfo, boolean hidden, boolean star, String password) {
        this.applicationInfo = applicationInfo;
        this.hidden = hidden;
        this.star = star;
        this.password = password;
    }
}
