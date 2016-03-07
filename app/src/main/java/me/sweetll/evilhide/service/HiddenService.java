package me.sweetll.evilhide.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by sweet on 3/6/16.
 */
public class HiddenService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public static void performAction(Context context, String action) {
        performAction(context, action, null);
    }

    public static void performAction(Context context, String action, Bundle extras) {
        if ((context == null) || (action == null) || action.equals("")) return;

        Intent svc = new Intent(context, HiddenService.class);
        svc.setAction(action);
        if (extras != null) svc.putExtras(extras);
        context.startService(svc);
    }

    public HiddenService() {
        super("hidden-service");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        if ((action == null) || (action.equals(""))) return;

        Shell.SU.run(action);

    }

}
