package me.sweetll.evilhide.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager

import me.sweetll.evilhide.MainActivity
import me.sweetll.evilhide.Settings
import me.sweetll.evilhide.SubstituteActivity

class CallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        var phoneNumber: String? = resultData
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val launchPassword = preferences.getString(Settings.KEY_PREF_LAUNCH_PASSWORD, "#1234")
        val selfInvisible = preferences.getBoolean(Settings.KEY_PREF_INVISIBLE, false)
        if (phoneNumber == null) {
            phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
        }

        if (phoneNumber == launchPassword) {
            resultData = null
            val i = Intent(
                    context,
                    if (selfInvisible) SubstituteActivity::class.java else MainActivity::class.java
            )
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        } else {
            //查询preference
        }

    }
}
