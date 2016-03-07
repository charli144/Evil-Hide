package me.sweetll.evilhide.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import me.sweetll.evilhide.MainActivity;

/**
 * Created by sweet on 3/6/16.
 */
public class CallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String phoneNumber = getResultData();
        if (phoneNumber == null) {
            phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        }

        if (phoneNumber.equals("#1234")) {
            setResultData(null);
            Intent i = new Intent();
            i.setClass(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }

    }
}
