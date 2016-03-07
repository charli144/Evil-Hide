package me.sweetll.evilhide.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import me.sweetll.evilhide.MainActivity;
import me.sweetll.evilhide.Settings;

/**
 * Created by sweet on 3/6/16.
 */
public class CallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String phoneNumber = getResultData();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String launchPassword = preferences.getString(Settings.SHARED_LAUNCH_PASSWORD, null);
        if (phoneNumber == null) {
            phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        }

        if (phoneNumber.equals(launchPassword)) {
            setResultData(null);
            Intent i = new Intent();
            i.setClass(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } else {
            //查询preference

        }

    }
}
