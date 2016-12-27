package me.sweetll.evilhide.config

import android.content.Context
import android.content.SharedPreferences
import me.sweetll.evilhide.AppApplication

object SharedPreferenceConfig {
    val FILE_NAME = "me.sweetll.evilhide_app"

    val PREFIX_PREF_KEY_B_FAVORITE = "favorite_"
    val PREFIX_PREF_KEY_B_HIDDEN = "hidden_"
    val PREFIX_PREF_KEY_S_PASSWORD = "password_"

    val sp: SharedPreferences by lazy {
        AppApplication.get()
                .getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    }
}
