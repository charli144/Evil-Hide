package me.sweetll.evilhide.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager

import me.sweetll.evilhide.MainActivity
import me.sweetll.evilhide.config.Settings
import me.sweetll.evilhide.SubstituteActivity

class CallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        var phoneNumber: String? = resultData
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val launchPassword = preferences.getString(Settings.KEY_PREF_LAUNCH_PASSWORD, "#1234")
        if (phoneNumber == null) {
            phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
        }

        if (phoneNumber == launchPassword) {
            val i = Intent()
            i.setClassName("me.sweetll.evilhide", "me.sweetll.evilhide.SubstituteActivity")
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
            resultData = null
        } else {
            //查询preference
        }

    }
}
