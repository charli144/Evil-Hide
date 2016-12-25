package me.sweetll.evilhide

import android.app.Application
import android.preference.PreferenceManager
import com.karumi.dexter.Dexter
import com.orhanobut.logger.Logger

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Logger.init()
        Dexter.initialize(this)
        PreferenceManager.setDefaultValues(this, R.xml.settings, false)
    }
}