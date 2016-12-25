package me.sweetll.evilhide.service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.os.Bundle

import eu.chainfire.libsuperuser.Shell

class HiddenService : IntentService("hidden-service") {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            val action = it.action
            if (!action.isNullOrEmpty()) {
                Shell.SU.run(action)
            }
        }
    }

    companion object {
        @JvmOverloads fun performAction(context: Context?, action: String?, extras: Bundle? = null) {
            if (context == null || action == null || action == "") return

            val svc = Intent(context, HiddenService::class.java)
            svc.action = action
            if (extras != null) svc.putExtras(extras)
            context.startService(svc)
        }
    }

}
